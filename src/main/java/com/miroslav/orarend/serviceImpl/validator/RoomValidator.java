package com.miroslav.orarend.serviceImpl.validator;

import com.miroslav.orarend.repository.RoomRepository;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RoomValidator {

    private final RoomRepository roomRepository;

    public RoomValidator(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }
    public boolean validateRoomMap(Map<String, String> roomData) {
        return roomData.containsKey("name");
    }

    public boolean doesRoomExist(String roomName) {
        return roomRepository.findByName(roomName) != null;
    }
}
