package com.miroslav.orarend.dto.input;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ToDoInputDTO {

    @NotEmpty
    private String title;

    @NotEmpty
    private String description;

    @NotNull
    private LocalDateTime dueTime;

    private Boolean isItDone;
}
