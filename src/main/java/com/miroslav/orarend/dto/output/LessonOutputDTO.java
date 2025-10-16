package com.miroslav.orarend.dto.output;

import com.miroslav.orarend.constants.DayOfWeek;
import lombok.Data;

import java.time.LocalTime;

@Data
public class LessonOutputDTO {

    private Long id;

    private String className;

    private String teacher;

    private DayOfWeek dayOfWeek;

    private LocalTime startTime;

    private LocalTime endTime;
}
