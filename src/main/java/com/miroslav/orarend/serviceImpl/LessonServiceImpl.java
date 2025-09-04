package com.miroslav.orarend.serviceImpl;

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

    private final LessonValidator validator;

    public LessonServiceImpl(LessonRepository repository, LessonValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    @Override
    public ResponseEntity<String> createLesson(Map<String, String> lessonData) {
        boolean validateLessonData = validator.validateLessonMap(lessonData);
        if(validateLessonData){
            Lesson inputLesson = createLessonFromMap(lessonData);
            if(!validator.doesLessonAlreadyExist(inputLesson.getStartTime(),inputLesson.getEndTime())){
                repository.save(inputLesson);
                return new ResponseEntity<>("Sikeres mentés", HttpStatus.OK);
            }
            return new  ResponseEntity<>("Duplikált óra", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Hibás input adat", HttpStatus.BAD_REQUEST);
    }

    private Lesson createLessonFromMap(Map<String, String> lessonData) {
        Lesson lesson = new Lesson();
        lesson.setClassName(lessonData.get("className"));
        lesson.setDayOfWeek(DayOfWeek.valueOf(lessonData.get("dayOfWeek")));
        lesson.setStartTime(LocalTime.parse(lessonData.get("startTime")));
        lesson.setEndTime(LocalTime.parse(lessonData.get("endTime")));
        return lesson;
    }
}
