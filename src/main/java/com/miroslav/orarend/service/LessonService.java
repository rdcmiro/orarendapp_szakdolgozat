package com.miroslav.orarend.service;

import com.miroslav.orarend.dto.LessonInputDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface LessonService {
    ResponseEntity<String> createLesson(LessonInputDTO dto);
}
