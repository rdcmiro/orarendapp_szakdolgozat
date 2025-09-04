package com.miroslav.orarend.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface RoomService {

    ResponseEntity<String> createRoom(Map<String, String> roomData);
}
