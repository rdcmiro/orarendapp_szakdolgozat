package com.miroslav.orarend.mapper;

import com.miroslav.orarend.dto.input.LessonInputDTO;
import com.miroslav.orarend.dto.output.LessonOutputDTO;
import com.miroslav.orarend.pojo.Lesson;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LessonMapper {

    Lesson toEntity(LessonInputDTO dto);

    LessonInputDTO toDto(Lesson entity);

    LessonOutputDTO toOutputDto(Lesson entity);

}
