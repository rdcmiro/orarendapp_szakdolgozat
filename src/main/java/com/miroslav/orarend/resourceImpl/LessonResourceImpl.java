package com.miroslav.orarend.resourceImpl;

import com.miroslav.orarend.dto.input.LessonInputDTO;
import com.miroslav.orarend.dto.output.LessonOutputDTO;
import com.miroslav.orarend.dto.patch.LessonPatchDTO;
import com.miroslav.orarend.resource.LessonResource;
import com.miroslav.orarend.service.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@Slf4j
@RequiredArgsConstructor
public class LessonResourceImpl implements LessonResource {

    private final LessonService lessonService;

    @Override
    public ResponseEntity<String> createLesson(LessonInputDTO dto) {
        try {
            return lessonService.createLesson(dto);
        } catch (Exception e){
            log.warn("Hiba óra felvétele közben" + e.getMessage());
            return ResponseEntity.internalServerError().body("Error");
        }
    }

    @Override
    public ResponseEntity<String> updateLesson(Long lessonId, LessonInputDTO dto) {
        try {
            return lessonService.updateLesson(lessonId, dto);
        } catch (Exception e) {
            log.warn("Hiba az óra frissítése közben" + e.getMessage());
            return ResponseEntity.internalServerError().body("Error");
        }
    }

    @Override
    public ResponseEntity<String> patchLesson(Long lessonId, LessonPatchDTO dto) {
        try {
            return lessonService.patchLesson(lessonId, dto);
        } catch (Exception e) {
            log.warn("Hiba a tanóra PATCH frissítésekor" + e.getMessage());
            return ResponseEntity.internalServerError().body("Error");
        }
    }

    @Override
    public ResponseEntity<LessonOutputDTO> getLesson(Long lessonId) {
        try {
            return lessonService.getLesson(lessonId);
        } catch (Exception e) {
            log.warn("Hiba a tanóra lekérésekor" + e.getMessage());
            return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<String> deleteLesson(Long lessonId) {
        try {
            return lessonService.deleteLesson(lessonId);
        } catch (Exception e) {
            log.warn("Hiba a tanóra törlésekor" + e.getMessage());
            return ResponseEntity.internalServerError().body("Error");
        }

    }

    @Override
    public ResponseEntity<List<LessonOutputDTO>> getAllUserLessons() {
        try{
            return lessonService.getAllByUser();
        }catch (Exception e){
            log.warn("Hiba a tanórák lekérésekor" + e.getMessage());
            return ResponseEntity.internalServerError().body(new ArrayList<>());
        }
    }
}
