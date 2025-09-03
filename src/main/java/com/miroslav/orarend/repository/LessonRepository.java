package com.miroslav.orarend.repository;

import com.miroslav.orarend.pojo.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
