package com.miroslav.orarend.serviceImpl;

import com.miroslav.orarend.dto.RoomInputDTO;
import com.miroslav.orarend.mapper.RoomMapper;
import com.miroslav.orarend.pojo.Room;
import com.miroslav.orarend.repository.RoomRepository;
import com.miroslav.orarend.service.RoomService;
import com.miroslav.orarend.serviceImpl.validator.RoomValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomValidator validator;

    private final RoomRepository roomRepository;

    private final RoomMapper roomMapper;

    public RoomServiceImpl(RoomValidator validator, RoomRepository roomRepository, RoomMapper roomMapper) {
        this.validator = validator;
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
    }

    @Override
    public ResponseEntity<String> createRoom(RoomInputDTO roomInputDTO) {
        Room room = roomMapper.toEntity(roomInputDTO);
        if(validator.doesRoomExist(room)){
            return ResponseEntity.badRequest().body("Invalid room data or room already exists");
        }
        roomRepository.save(room);
        return ResponseEntity.ok("Room created successfully");
    }
}
