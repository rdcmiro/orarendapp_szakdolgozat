package com.miroslav.orarend.dto.output;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FileEntityOutputDTO {

    private Long id;

    private String filename;

    private String contentType;

    private String filePath;

    private LocalDateTime uploadedAt;
}
