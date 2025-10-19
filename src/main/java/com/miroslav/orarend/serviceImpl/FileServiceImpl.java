package com.miroslav.orarend.serviceImpl;

import com.miroslav.orarend.dto.output.FileEntityOutputDTO;
import com.miroslav.orarend.mapper.FileEntityMapper;
import com.miroslav.orarend.pojo.FileEntity;
import com.miroslav.orarend.pojo.Lesson;
import com.miroslav.orarend.pojo.User;
import com.miroslav.orarend.repository.FileRepository;
import com.miroslav.orarend.repository.UserRepository;
import com.miroslav.orarend.service.FileService;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final UserRepository userRepository;
    private final FileEntityMapper fileEntityMapper;

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
        User user = userRepository.findByEmail(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        List<FileEntity> files = fileRepository.findAllByUser(user);

        List<FileEntityOutputDTO> result = files.stream()
                .map(fileEntityMapper::toOutputDTO)
                .toList();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
