package com.miroslav.orarend.serviceImpl;

import com.miroslav.orarend.dto.output.FileEntityOutputDTO;
import com.miroslav.orarend.mapper.FileEntityMapper;
import com.miroslav.orarend.pojo.FileEntity;
import com.miroslav.orarend.pojo.Lesson;
import com.miroslav.orarend.pojo.User;
import com.miroslav.orarend.repository.FileRepository;
import com.miroslav.orarend.service.FileService;
import com.miroslav.orarend.utils.OrarendUtil;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final FileEntityMapper fileEntityMapper;
    private final OrarendUtil orarendUtil;

    @Value("${app.upload-dir}")
    private String uploadDir;

    @Override
    public FileEntity saveFile(MultipartFile file, User user, Long lessonId) throws IOException {
        // létrehozza a könyvtárat, ha nem létezik
        Files.createDirectories(Path.of(uploadDir));

        Path target = Path.of(uploadDir, System.currentTimeMillis() + "_" + file.getOriginalFilename());
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        FileEntity fe = new FileEntity();
        fe.setFilename(file.getOriginalFilename());
        fe.setContentType(file.getContentType());
        fe.setFilePath(target.toString());
        fe.setUploadedAt(LocalDateTime.now());
        fe.setUser(user);

        if (lessonId != null) {
            Lesson lesson = new Lesson();
            lesson.setId(lessonId);
            fe.setLesson(lesson);
        }

        return fileRepository.save(fe);
    }

    @Override
    public String extractTextFromFile(Long fileId) throws Exception {
        FileEntity fe = fileRepository.findById(fileId).orElseThrow();
        Path path = Path.of(fe.getFilePath());
        String name = fe.getFilename().toLowerCase();

        if (name.endsWith(".pdf")) {
            try (PDDocument doc = PDDocument.load(path.toFile())) {
                return new PDFTextStripper().getText(doc);
            }
        }

        if (name.endsWith(".docx")) {
            try (InputStream is = Files.newInputStream(path);
                 XWPFDocument doc = new XWPFDocument(is)) {
                return doc.getParagraphs()
                        .stream()
                        .map(p -> p.getText())
                        .reduce("", (a, b) -> a + "\n" + b);
            }
        }

        // ha sima txt vagy más olvasható
        return Files.readString(path);
    }

    @Override
    public ResponseEntity<List<FileEntityOutputDTO>> getAllByUser() {
        User user = orarendUtil.getAuthenticatedUser();

        List<FileEntity> files = fileRepository.findAllByUser(user);

        List<FileEntityOutputDTO> result = files.stream()
                .map(fileEntityMapper::toOutputDTO)
                .toList();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteFile(Long id) {
        User user = orarendUtil.getAuthenticatedUser();

        FileEntity file = fileRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found"));

        if (!file.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to delete this lesson");
        }

        fileRepository.delete(file);
        return ResponseEntity.ok("File deleted");
    }

    @Override
    public ResponseEntity<Resource> downloadFile(Long id, User user) throws IOException {
        FileEntity file = fileRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found"));

        // jogosultság ellenőrzés
        if (!file.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to download this file");
        }

        Path path = Path.of(file.getFilePath());
        if (!Files.exists(path)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Physical file not found on disk");
        }

        Resource resource = new UrlResource(path.toUri());
        String contentType = file.getContentType();
        if (contentType == null || contentType.isBlank()) {
            contentType = Files.probeContentType(path);
            if (contentType == null) contentType = "application/octet-stream";
        }

        String filename = file.getFilename();
        String encodedFilename = java.net.URLEncoder.encode(filename, java.nio.charset.StandardCharsets.UTF_8)
                .replaceAll("\\+", "%20");

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + filename + "\"; filename*=UTF-8''" + encodedFilename)
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-Disposition")
                .body(resource);
    }


}
