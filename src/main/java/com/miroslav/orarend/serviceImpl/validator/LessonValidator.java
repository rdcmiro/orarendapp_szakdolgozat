package com.miroslav.orarend.serviceImpl.validator;

import com.miroslav.orarend.constants.DayOfWeek;
import com.miroslav.orarend.pojo.User;
import com.miroslav.orarend.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class LessonValidator {

    private final LessonRepository repository;

    public boolean doesLessonAlreadyExist(LocalTime startTime, LocalTime endTime, User user, DayOfWeek dayOfWeek) {
        return repository.existsByStartTimeAndEndTimeAndDayOfWeekAndUser(startTime, endTime, dayOfWeek, user);
    }
}
