package com.miroslav.orarend.serviceImpl;

import com.miroslav.orarend.dto.input.LessonInputDTO;
import com.miroslav.orarend.dto.output.LessonOutputDTO;
import com.miroslav.orarend.dto.patch.LessonPatchDTO;
import com.miroslav.orarend.mapper.LessonMapper;
import com.miroslav.orarend.pojo.Lesson;
import com.miroslav.orarend.pojo.User;
import com.miroslav.orarend.repository.LessonRepository;
import com.miroslav.orarend.repository.UserRepository;
import com.miroslav.orarend.service.LessonService;
import com.miroslav.orarend.serviceImpl.validator.LessonValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository repository;
    private final LessonMapper lessonMapper;
    private final UserRepository userRepository;
    private final LessonValidator lessonValidator;

    // CREATE
    @Override
    public ResponseEntity<String> createLesson(LessonInputDTO dto) {
        Lesson input = lessonMapper.toEntity(dto);

        User user = userRepository.findByEmail(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        input.setUser(user);

        boolean exists = lessonValidator.doesLessonAlreadyExist(
                input.getStartTime(), input.getEndTime(), user, input.getDayOfWeek()
        );

        if (exists) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Lesson already exists");
        }

        repository.save(input);
        return ResponseEntity.ok("Lesson created");
    }

    // UPDATE (PUT)
    @Override
    public ResponseEntity<String> updateLesson(Long lessonId, LessonInputDTO dto) {
        Lesson lessonToUpdate = repository.findById(lessonId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lesson not found"));

        User user = userRepository.findByEmail(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        if (!lessonToUpdate.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to edit this lesson");
        }

        lessonToUpdate.setClassName(dto.getClassName());
        lessonToUpdate.setTeacher(dto.getTeacher());
        lessonToUpdate.setDayOfWeek(dto.getDayOfWeek());
        lessonToUpdate.setStartTime(dto.getStartTime());
        lessonToUpdate.setEndTime(dto.getEndTime());

        repository.save(lessonToUpdate);
        return ResponseEntity.ok("Lesson updated");
    }

    // PATCH (részleges update)
    @Override
    public ResponseEntity<String> patchLesson(Long lessonId, LessonPatchDTO dto) {
        Lesson lessonToUpdate = repository.findById(lessonId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lesson not found"));

        User user = userRepository.findByEmail(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        if (!lessonToUpdate.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to modify this lesson");
        }

        if (dto.getClassName() != null) lessonToUpdate.setClassName(dto.getClassName());
        if (dto.getTeacher() != null) lessonToUpdate.setTeacher(dto.getTeacher());
        if (dto.getDayOfWeek() != null) lessonToUpdate.setDayOfWeek(dto.getDayOfWeek());
        if (dto.getStartTime() != null) lessonToUpdate.setStartTime(dto.getStartTime());
        if (dto.getEndTime() != null) lessonToUpdate.setEndTime(dto.getEndTime());

        repository.save(lessonToUpdate);
        return ResponseEntity.ok("Lesson patched");
    }

    // GET (egyetlen lesson lekérése)
    @Override
    public ResponseEntity<LessonOutputDTO> getLesson(Long lessonId) {
        User user = userRepository.findByEmail(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        Lesson lesson = repository.findById(lessonId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lesson not found"));

        if (!lesson.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to access this lesson");
        }

        LessonOutputDTO output = lessonMapper.toOutputDto(lesson);
        return ResponseEntity.ok(output);
    }

    // DELETE
    @Override
    public ResponseEntity<String> deleteLesson(Long lessonId) {
        User user = userRepository.findByEmail(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        Lesson lesson = repository.findById(lessonId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lesson not found"));

        if (!lesson.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to delete this lesson");
        }

        repository.delete(lesson);
        return ResponseEntity.ok("Lesson deleted");
    }

    // GET ALL by user
    @Override
    public ResponseEntity<List<LessonOutputDTO>> getAllByUser() {
        User user = userRepository.findByEmail(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        List<Lesson> lessons = repository.findByUser(user);

        if (lessons.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        List<LessonOutputDTO> result = lessons.stream()
                .map(lessonMapper::toOutputDto)
                .toList();

        return ResponseEntity.ok(result);
    }
}
