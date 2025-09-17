package com.miroslav.orarend.serviceImpl.validator;

import com.miroslav.orarend.repository.LessonRepository;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Map;

@Component
public class LessonValidator {

    private final LessonRepository repository;

    public LessonValidator(LessonRepository repository) {
        this.repository = repository;
    }


    public boolean doesLessonAlreadyExist(LocalTime startTime, LocalTime endTime) {
        return repository.existsByStartTimeAndEndTime(startTime, endTime);
    }
}
