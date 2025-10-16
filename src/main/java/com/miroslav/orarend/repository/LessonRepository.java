package com.miroslav.orarend.repository;

import com.miroslav.orarend.constants.DayOfWeek;
import com.miroslav.orarend.pojo.Lesson;
import com.miroslav.orarend.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    boolean existsByStartTimeAndEndTime(LocalTime startTime, LocalTime endTime);

    boolean existsByStartTimeAndEndTimeAndDayOfWeekAndUser(LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek, User user);

    List<Lesson> findByUser(User user);

}
