package com.miroslav.orarend.serviceImpl;

import com.miroslav.orarend.dto.LessonInputDTO;
import com.miroslav.orarend.dto.LessonOutputDTO;
import com.miroslav.orarend.dto.LessonPatchDTO;
import com.miroslav.orarend.mapper.LessonMapper;
import com.miroslav.orarend.pojo.Lesson;
import com.miroslav.orarend.pojo.User;
import com.miroslav.orarend.repository.LessonRepository;
import com.miroslav.orarend.repository.UserRepository;
import com.miroslav.orarend.service.LessonService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Optional;

@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository repository;

    private final LessonMapper mapper;
    private final LessonMapper lessonMapper;
    private final UserRepository userRepository;

    public LessonServiceImpl(LessonRepository repository, LessonMapper mapper, LessonMapper lessonMapper, UserRepository userRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.lessonMapper = lessonMapper;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<String> createLesson(LessonInputDTO dto) {
        Lesson input = mapper.toEntity(dto);

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        input.setUser(user);

        if(!doesLessonAlreadyExist(input.getStartTime(), input.getEndTime())) {
            repository.save(input);
            return new ResponseEntity<>("Lesson created", HttpStatus.CREATED);
            }
        else  {
            return new ResponseEntity<>("Lesson already exists", HttpStatus.CONFLICT);
        }
    }

    private boolean doesLessonAlreadyExist(LocalTime startTime, LocalTime endTime) {
        return repository.existsByStartTimeAndEndTime(startTime, endTime);
    }

    @Override
    public ResponseEntity<String> updateLesson(Long lessonId, LessonInputDTO dto) {
        Optional<Lesson> lesson = repository.findById(lessonId);

        if(lesson.isEmpty()) {
            return new ResponseEntity<>("Lesson not found", HttpStatus.NOT_FOUND);
        }

        Lesson lessonToUpdate = lesson.get();
        lessonToUpdate.setClassName(dto.getClassName());
        lessonToUpdate.setDayOfWeek(dto.getDayOfWeek());
        lessonToUpdate.setStartTime(dto.getStartTime());
        lessonToUpdate.setEndTime(dto.getEndTime());
        repository.save(lessonToUpdate);
        return new ResponseEntity<>("Lesson updated", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> patchLesson(Long lessonId, LessonPatchDTO dto) {
        Optional<Lesson> lesson = repository.findById(lessonId);

        if(lesson.isEmpty()) {
            return new ResponseEntity<>("Lesson not found", HttpStatus.NOT_FOUND);
        }

        Lesson lessonToUpdate = lesson.get();
        if(dto.getClassName() != null) {
            lessonToUpdate.setClassName(dto.getClassName());
        }
        if(dto.getTeacher() != null) {
            lessonToUpdate.setTeacher(dto.getTeacher());
        }
        if(dto.getDayOfWeek() != null) {
            lessonToUpdate.setDayOfWeek(dto.getDayOfWeek());
        }
        if(dto.getStartTime() != null) {
            lessonToUpdate.setStartTime(dto.getStartTime());
        }
        if(dto.getEndTime() != null) {
            lessonToUpdate.setEndTime(dto.getEndTime());
        }
        repository.save(lessonToUpdate);
        return new ResponseEntity<>("Lesson patched", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<LessonOutputDTO> getLesson(Long lessonId) {
        Optional<Lesson> lesson = repository.findById(lessonId);
        if(lesson.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        LessonOutputDTO lessonOutputDTO = lessonMapper.toOutputDto(lesson.get());
        return new ResponseEntity<>(lessonOutputDTO, HttpStatus.OK);
    }

    public ResponseEntity<String> deleteLesson(Long lessonId) {
        try {
            repository.deleteById(lessonId);
            return ResponseEntity.ok("Lesson deleted");
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found");
        }
    }

}
