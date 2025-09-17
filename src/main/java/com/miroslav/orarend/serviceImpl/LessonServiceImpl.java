package com.miroslav.orarend.serviceImpl;

import com.miroslav.orarend.dto.LessonInputDTO;
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
}
