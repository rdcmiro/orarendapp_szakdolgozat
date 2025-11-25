package com.miroslav.orarend.resource;

import com.miroslav.orarend.dto.input.LessonInputDTO;
import com.miroslav.orarend.dto.output.LessonOutputDTO;
import com.miroslav.orarend.dto.patch.LessonPatchDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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

    @GetMapping("/allUserLessons")
    ResponseEntity<List<LessonOutputDTO>> getAllUserLessons();

}
