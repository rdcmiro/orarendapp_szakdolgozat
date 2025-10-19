package com.miroslav.orarend.dto.input;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;

@Data
public class ToDoInputDTO {

    @NotEmpty
    private String title;

    @NotEmpty
    private String description;

    @NotNull
    private LocalTime dueTime;

    private Boolean isItDone;
}
