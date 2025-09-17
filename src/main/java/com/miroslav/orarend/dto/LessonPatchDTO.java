package com.miroslav.orarend.dto;

import com.miroslav.orarend.constants.DayOfWeek;

import lombok.Data;

import java.time.LocalTime;

@Data
public class LessonPatchDTO {

    private String className;

    private String teacher;

    private DayOfWeek dayOfWeek;

    private LocalTime startTime;

    private LocalTime endTime;
}

