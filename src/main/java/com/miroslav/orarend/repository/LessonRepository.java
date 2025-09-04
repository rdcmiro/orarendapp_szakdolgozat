package com.miroslav.orarend.repository;

import com.miroslav.orarend.pojo.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    boolean existsByStartTimeAndEndTime(LocalTime startTime, LocalTime endTime);

}
