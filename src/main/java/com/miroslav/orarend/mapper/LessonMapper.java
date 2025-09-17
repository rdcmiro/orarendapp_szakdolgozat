package com.miroslav.orarend.mapper;

import com.miroslav.orarend.dto.LessonInputDTO;
import com.miroslav.orarend.pojo.Lesson;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LessonMapper {

    Lesson toEntity(LessonInputDTO dto);
    LessonInputDTO toDto(Lesson entity);

}
