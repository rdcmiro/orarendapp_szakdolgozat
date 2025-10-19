package com.miroslav.orarend.mapper;

import com.miroslav.orarend.dto.output.FileEntityOutputDTO;
import com.miroslav.orarend.pojo.FileEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileEntityMapper {

    FileEntityOutputDTO toOutputDTO(FileEntity entity);
}
