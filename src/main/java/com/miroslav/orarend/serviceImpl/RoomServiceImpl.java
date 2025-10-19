package com.miroslav.orarend.serviceImpl;

import com.miroslav.orarend.dto.input.RoomInputDTO;
import com.miroslav.orarend.dto.output.RoomOutputDTO;
import com.miroslav.orarend.dto.patch.RoomPatchDTO;
import com.miroslav.orarend.mapper.RoomMapper;
import com.miroslav.orarend.pojo.Room;
import com.miroslav.orarend.pojo.User;
import com.miroslav.orarend.repository.RoomRepository;
import com.miroslav.orarend.repository.UserRepository;
import com.miroslav.orarend.service.RoomService;
import com.miroslav.orarend.serviceImpl.validator.RoomValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomValidator validator;
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final UserRepository userRepository;

    // 🔹 Helper metódus a bejelentkezett user lekérésére
    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
    }

    // 🔹 CREATE
    @Override
    public ResponseEntity<String> createRoom(RoomInputDTO roomInputDTO) {
        Room room = roomMapper.toEntity(roomInputDTO);
        User user = getCurrentUser();
        room.setCreatedBy(user);

        if (validator.doesRoomExist(room)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Room already exists or invalid data");
        }

        roomRepository.save(room);
        return ResponseEntity.ok("Room created successfully");
    }

    // 🔹 UPDATE (PUT)
    @Override
    public ResponseEntity<String> updateRoom(Long roomId, RoomInputDTO roomInputDTO) {
        Room existingRoom = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));

        User user = getCurrentUser();
        if (!existingRoom.getCreatedBy().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to edit this room");
        }

        existingRoom.setName(roomInputDTO.getName());
        roomRepository.save(existingRoom);
        return ResponseEntity.ok("Room updated successfully");
    }

    // 🔹 PATCH (részleges frissítés)
    @Override
    public ResponseEntity<String> patchRoom(Long roomId, RoomPatchDTO patchDTO) {
        Room existingRoom = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));

        User user = getCurrentUser();
        if (!existingRoom.getCreatedBy().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to modify this room");
        }

        if (patchDTO.getName() != null) {
            existingRoom.setName(patchDTO.getName());
        }

        roomRepository.save(existingRoom);
        return ResponseEntity.ok("Room patched successfully");
    }

    // 🔹 GET (egyetlen szoba)
    @Override
    public ResponseEntity<RoomOutputDTO> getRoom(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));

        User user = getCurrentUser();
        if (!room.getCreatedBy().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to access this room");
        }

        RoomOutputDTO output = roomMapper.toOutputDto(room);
        return ResponseEntity.ok(output);
    }

    // 🔹 DELETE
    @Override
    public ResponseEntity<String> deleteRoom(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));

        User user = getCurrentUser();
        if (!room.getCreatedBy().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to delete this room");
        }

        roomRepository.delete(room);
        return ResponseEntity.ok("Room deleted successfully");
    }
}
