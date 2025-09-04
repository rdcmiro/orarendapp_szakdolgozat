package com.miroslav.orarend.resourceImpl;

import com.miroslav.orarend.resource.LessonResource;
import com.miroslav.orarend.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LessonResourceImpl implements LessonResource {

    @Autowired
    private LessonService lessonService;

    @Override
    public ResponseEntity<String> createLesson(Map<String, String> lessonData) {
        try {
            return lessonService.createLesson(lessonData);
        } catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().body("Error");
    }
}
