package com.miroslav.orarend.mapper;


import com.miroslav.orarend.dto.UserInputDTO;
import com.miroslav.orarend.dto.UserOutputDTO;
import com.miroslav.orarend.pojo.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserInputDTO userInputDTO);

    UserInputDTO toDTO(User user);

    @Mapping(target = "password", ignore = true)
    UserOutputDTO toOutputDTO(User user);
}
