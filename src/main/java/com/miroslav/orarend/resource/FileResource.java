package com.miroslav.orarend.resource;


import com.miroslav.orarend.dto.output.FileEntityOutputDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/files")
public interface FileResource {

    @PostMapping("/upload")
    ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "lessonId", required = false) Long lessonId,
            Authentication authentication
    );

    @GetMapping("/{id}/summary")
    ResponseEntity<?> summarizeFile(@PathVariable Long id);

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteFile(@PathVariable Long id);

    @GetMapping("/getAllFilesByUser")
    ResponseEntity<List<FileEntityOutputDTO>> getAllFilesByUser();

    @GetMapping("/{id}/download")
    ResponseEntity<?> downloadFile(@PathVariable Long id, Authentication authentication);

}
