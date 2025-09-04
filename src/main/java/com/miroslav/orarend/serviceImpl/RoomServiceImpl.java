package com.miroslav.orarend.serviceImpl;

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

    public RoomServiceImpl(RoomValidator validator, RoomRepository roomRepository) {
        this.validator = validator;
        this.roomRepository = roomRepository;
    }

    @Override
    public ResponseEntity<String> createRoom(Map<String, String> roomData) {
        boolean roomNumber = validator.validateRoomMap(roomData) &&
                !validator.doesRoomExist(roomData.get("name"));
        if(!roomNumber){
            return ResponseEntity.badRequest().body("Invalid room data or room already exists");
        }
        Room inputRoom = createRoomFromMap(roomData);
        roomRepository.save(inputRoom);
        return ResponseEntity.ok("Room created successfully");
    }
    private Room createRoomFromMap(Map<String, String> roomData) {
        Room room = new Room();
        String name = roomData.get("name");
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Room name is missing or empty");
        }
        room.setName(name);
        return room;
    }
}
