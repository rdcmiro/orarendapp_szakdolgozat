package com.miroslav.orarend.resource;

import com.miroslav.orarend.dto.LessonInputDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping("/lessons")
public interface LessonResource {

    @PostMapping("/create")
    ResponseEntity<String> createLesson(@RequestBody LessonInputDTO lessonData);

}
