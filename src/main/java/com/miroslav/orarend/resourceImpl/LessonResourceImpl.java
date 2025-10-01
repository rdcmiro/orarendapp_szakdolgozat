package com.miroslav.orarend.resourceImpl;

import com.miroslav.orarend.dto.input.LessonInputDTO;
import com.miroslav.orarend.dto.output.LessonOutputDTO;
import com.miroslav.orarend.dto.patch.LessonPatchDTO;
import com.miroslav.orarend.resource.LessonResource;
import com.miroslav.orarend.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class LessonResourceImpl implements LessonResource {

    @Autowired
    private LessonService lessonService;

    @Override
    public ResponseEntity<String> createLesson(LessonInputDTO dto) {
        try {
            return lessonService.createLesson(dto);
        } catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().body("Error");
    }

    @Override
    public ResponseEntity<String> updateLesson(Long lessonId, LessonInputDTO dto) {
        try {
            return lessonService.updateLesson(lessonId, dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().body("Error");
    }

    @Override
    public ResponseEntity<String> patchLesson(Long lessonId, LessonPatchDTO dto) {
        try {
            return lessonService.patchLesson(lessonId, dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().body("Error");
    }

    @Override
    public ResponseEntity<LessonOutputDTO> getLesson(Long lessonId) {
        try {
            return lessonService.getLesson(lessonId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> deleteLesson(Long lessonId) {
        try {
            return lessonService.deleteLesson(lessonId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().body("Error");
    }
}
