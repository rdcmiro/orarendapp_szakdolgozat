package com.miroslav.orarend.dto.input;

import com.miroslav.orarend.constants.DayOfWeek;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.time.LocalTime;

@Data
public class LessonInputDTO {

    @NotEmpty
    private String className;

    private String teacher;

    @NotNull
    private DayOfWeek dayOfWeek;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;
}
