package com.miroslav.orarend.dto.output;

import lombok.Data;

import java.time.LocalTime;

@Data
public class ToDoOutputDTO {

    private Long id;

    private String title;

    private String description;

    private LocalTime dueTime;

    private Boolean isItDone;
}
