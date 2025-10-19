package com.miroslav.orarend.dto.patch;

import lombok.Data;

import java.time.LocalTime;

@Data
public class ToDoPatchDTO {

    private String title;

    private String description;

    private LocalTime dueTime;

    private Boolean isItDone;
}
