package com.miroslav.orarend.service;

import com.miroslav.orarend.dto.RoomInputDTO;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface RoomService {

    ResponseEntity<String> createRoom(RoomInputDTO roomInputDTO);
}
