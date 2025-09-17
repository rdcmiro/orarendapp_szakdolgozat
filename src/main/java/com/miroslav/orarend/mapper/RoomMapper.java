package com.miroslav.orarend.mapper;


import com.miroslav.orarend.dto.RoomInputDTO;
import com.miroslav.orarend.pojo.Room;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    Room toEntity(RoomInputDTO roomInputDTO);
    RoomInputDTO toDTO(Room room);
}
