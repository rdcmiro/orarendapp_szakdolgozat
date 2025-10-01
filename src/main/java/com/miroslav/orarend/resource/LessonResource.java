package com.miroslav.orarend.resource;

import com.miroslav.orarend.dto.input.LessonInputDTO;
import com.miroslav.orarend.dto.output.LessonOutputDTO;
import com.miroslav.orarend.dto.patch.LessonPatchDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/lessons")
public interface LessonResource {

    @PostMapping("/create")
    ResponseEntity<String> createLesson(@RequestBody LessonInputDTO lessonData);

    @PutMapping("/update/{lessonId}")
    ResponseEntity<String> updateLesson(@PathVariable Long lessonId, @Valid @RequestBody LessonInputDTO lessonData);

    @PatchMapping("/patch/{lessonId}")
    ResponseEntity<String> patchLesson(@PathVariable Long lessonId, @RequestBody LessonPatchDTO lessonData);

    @GetMapping("/get/{lessonId}")
    ResponseEntity<LessonOutputDTO> getLesson(@PathVariable Long lessonId);

    @DeleteMapping("/delete/{lessonId}")
    ResponseEntity<String> deleteLesson(@PathVariable Long lessonId);

}
