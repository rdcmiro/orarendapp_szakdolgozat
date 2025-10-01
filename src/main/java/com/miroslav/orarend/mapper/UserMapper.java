package com.miroslav.orarend.mapper;


import com.miroslav.orarend.dto.input.UserInputDTO;
import com.miroslav.orarend.dto.output.UserOutputDTO;
import com.miroslav.orarend.pojo.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserInputDTO userInputDTO);

    UserInputDTO toDTO(User user);

    UserOutputDTO toOutputDTO(User user);
}
