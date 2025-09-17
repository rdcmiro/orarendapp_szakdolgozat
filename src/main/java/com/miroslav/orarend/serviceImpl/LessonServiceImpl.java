package com.miroslav.orarend.serviceImpl;

import com.miroslav.orarend.dto.LessonInputDTO;
import com.miroslav.orarend.dto.LessonPatchDTO;
import com.miroslav.orarend.mapper.LessonMapper;
import com.miroslav.orarend.pojo.Lesson;
import com.miroslav.orarend.repository.LessonRepository;
import com.miroslav.orarend.service.LessonService;
import com.miroslav.orarend.serviceImpl.validator.LessonValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;

@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository repository;

    private final LessonMapper mapper;

    public LessonServiceImpl(LessonRepository repository, LessonValidator validator, LessonMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<String> createLesson(LessonInputDTO dto) {
        Lesson input = mapper.toEntity(dto);
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
}
