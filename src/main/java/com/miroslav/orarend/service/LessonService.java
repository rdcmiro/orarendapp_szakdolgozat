package com.miroslav.orarend.service;

import com.miroslav.orarend.dto.LessonInputDTO;
import com.miroslav.orarend.dto.LessonPatchDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface LessonService {
    ResponseEntity<String> createLesson(LessonInputDTO dto);

    ResponseEntity<String> updateLesson(Long lessonId, LessonInputDTO dto);

    ResponseEntity<String> patchLesson(Long lessonId, LessonPatchDTO dto);
}
