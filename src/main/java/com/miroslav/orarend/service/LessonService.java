package com.miroslav.orarend.service;

import com.miroslav.orarend.dto.input.LessonInputDTO;
import com.miroslav.orarend.dto.output.LessonOutputDTO;
import com.miroslav.orarend.dto.patch.LessonPatchDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface LessonService {
    ResponseEntity<String> createLesson(LessonInputDTO dto);

    ResponseEntity<String> updateLesson(Long lessonId, LessonInputDTO dto);

    ResponseEntity<String> patchLesson(Long lessonId, LessonPatchDTO dto);

    ResponseEntity<LessonOutputDTO> getLesson(Long lessonId);

    ResponseEntity<String> deleteLesson(Long lessonId);
}
