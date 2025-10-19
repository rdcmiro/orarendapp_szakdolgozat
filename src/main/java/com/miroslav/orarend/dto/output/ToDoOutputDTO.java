package com.miroslav.orarend.dto.output;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ToDoOutputDTO {

    private Long id;

    private String title;

    private String description;

    private LocalDateTime dueTime;

    private Boolean isItDone;
}
