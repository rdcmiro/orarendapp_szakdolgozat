package com.miroslav.orarend.service;

import com.miroslav.orarend.dto.input.RoomInputDTO;
import com.miroslav.orarend.dto.output.RoomOutputDTO;
import com.miroslav.orarend.dto.patch.RoomPatchDTO;
import org.springframework.http.ResponseEntity;

public interface RoomService {

    ResponseEntity<String> createRoom(RoomInputDTO roomInputDTO);

    ResponseEntity<String> updateRoom(Long roomId, RoomInputDTO roomInputDTO);

    ResponseEntity<String> patchRoom(Long roomId, RoomPatchDTO roomPatchDTO);

    ResponseEntity<RoomOutputDTO> getRoom(Long roomId);

    ResponseEntity<String> deleteRoom(Long roomId);
}
