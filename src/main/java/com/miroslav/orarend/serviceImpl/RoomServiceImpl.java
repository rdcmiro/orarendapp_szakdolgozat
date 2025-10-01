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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomValidator validator;

    private final RoomRepository roomRepository;

    private final RoomMapper roomMapper;

    private final UserRepository userRepository;

    public RoomServiceImpl(RoomValidator validator, RoomRepository roomRepository, RoomMapper roomMapper, UserRepository userRepository) {
        this.validator = validator;
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<String> createRoom(RoomInputDTO roomInputDTO) {
        Room room = roomMapper.toEntity(roomInputDTO);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        room.setCreatedBy(user);
        if (validator.doesRoomExist(room)) {
            return ResponseEntity.badRequest().body("Invalid room data or room already exists");
        }
        roomRepository.save(room);
        return ResponseEntity.ok("Room created successfully");
    }

    @Override
    public ResponseEntity<String> updateRoom(Long roomId, RoomInputDTO roomInputDTO) {
        Optional<Room> optionalRoom = roomRepository.findById(roomId);

        if (optionalRoom.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Room existingRoom = optionalRoom.get();
        existingRoom.setName(roomInputDTO.getName());
        roomRepository.save(existingRoom);
        return ResponseEntity.ok("Room updated successfully");
    }

    @Override
    public ResponseEntity<String> patchRoom(Long roomId, RoomPatchDTO patchDTO) {
        Optional<Room> optionalRoom = roomRepository.findById(roomId);
        if (optionalRoom.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Room existingRoom = optionalRoom.get();
        if (patchDTO.getName() != null) {
            existingRoom.setName(patchDTO.getName());
        }
        roomRepository.save(existingRoom);
        return ResponseEntity.ok("Room patched successfully");
    }

    @Override
    public ResponseEntity<RoomOutputDTO> getRoom(Long roomId) {
        Optional<Room> optionalRoom = roomRepository.findById(roomId);
        if (optionalRoom.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        RoomOutputDTO outputDTO = roomMapper.toOutputDto(optionalRoom.get());
        return ResponseEntity.ok(outputDTO);
    }

    @Override
    public ResponseEntity<String> deleteRoom(Long roomId) {
        try {
            roomRepository.deleteById(roomId);
            return ResponseEntity.ok("Room deleted successfully");
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
