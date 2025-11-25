package com.miroslav.orarend.service;

import com.miroslav.orarend.dto.output.FileEntityOutputDTO;
import com.miroslav.orarend.pojo.FileEntity;
import com.miroslav.orarend.pojo.User;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface FileService {

    FileEntity saveFile(MultipartFile file, User user, Long lessonId) throws IOException;


    String extractTextFromFile(Long fileId) throws Exception;

    ResponseEntity<List<FileEntityOutputDTO>> getAllByUser();

    ResponseEntity<?> deleteFile(Long id);

    ResponseEntity<Resource> downloadFile(Long id, User user) throws IOException;
}
