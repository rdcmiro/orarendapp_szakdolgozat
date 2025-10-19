package com.miroslav.orarend.mapper;

import com.miroslav.orarend.dto.input.ToDoInputDTO;
import com.miroslav.orarend.dto.output.ToDoOutputDTO;
import com.miroslav.orarend.pojo.ToDo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ToDoMapper {

    ToDo toEntity(ToDoInputDTO toDoInputDTO);

    ToDoOutputDTO toOutputDto(ToDo entity);
}
