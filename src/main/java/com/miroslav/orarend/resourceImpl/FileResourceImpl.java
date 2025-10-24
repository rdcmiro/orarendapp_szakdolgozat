package com.miroslav.orarend.resourceImpl;


import com.miroslav.orarend.dto.output.FileEntityOutputDTO;
import com.miroslav.orarend.pojo.FileEntity;
import com.miroslav.orarend.pojo.User;
import com.miroslav.orarend.resource.FileResource;
import com.miroslav.orarend.service.FileService;
import com.miroslav.orarend.service.OllamaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileResourceImpl implements FileResource {

    private final FileService fileService;
    private final OllamaService ollamaService;

    @Override
    public ResponseEntity<?> uploadFile(MultipartFile file, Long lessonId, Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            FileEntity saved = fileService.saveFile(file, user, lessonId);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("❌ Feltöltés sikertelen: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> summarizeFile(Long id) {
        try {
            String text = fileService.extractTextFromFile(id);
            if (text.length() > 15000) {
                text = text.substring(0, 15000);
            }
            String summary = ollamaService.summarizeText(text);
            return ResponseEntity.ok(summary);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("⚠️ Hiba az összefoglalás során: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> deleteFile(Long id) {
        try {
            return fileService.deleteFile(id);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body("Sikertelen törlés");
        }
    }

    @Override
    public ResponseEntity<List<FileEntityOutputDTO>> getAllFilesByUser() {
        try {
            return fileService.getAllByUser();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<?> downloadFile(Long id, Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return fileService.downloadFile(id, user);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("❌ Letöltési hiba: " + e.getMessage());
        }
    }

}
