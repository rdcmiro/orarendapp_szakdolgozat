package com.miroslav.orarend.service;

import com.miroslav.orarend.dto.RoomInputDTO;
import com.miroslav.orarend.dto.RoomPatchDTO;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface RoomService {

    ResponseEntity<String> createRoom(RoomInputDTO roomInputDTO);

    ResponseEntity<String> updateRoom(Long roomId, RoomInputDTO roomInputDTO);

    ResponseEntity<String> patchRoom(Long roomId, RoomPatchDTO roomPatchDTO);
}
