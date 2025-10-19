package com.miroslav.orarend.dto.patch;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ToDoPatchDTO {

    private String title;

    private String description;

    private LocalDateTime dueTime;

    private Boolean isItDone;
}
