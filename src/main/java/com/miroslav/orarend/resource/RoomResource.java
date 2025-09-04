package com.miroslav.orarend.resource;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping("/rooms")
public interface RoomResource {

    @PostMapping("/create")
    ResponseEntity<String> createRoom(@RequestBody Map<String, String> roomData);


}
