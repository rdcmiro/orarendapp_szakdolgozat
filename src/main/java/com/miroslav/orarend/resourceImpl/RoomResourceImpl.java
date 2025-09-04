package com.miroslav.orarend.resourceImpl;

import com.miroslav.orarend.resource.RoomResource;
import com.miroslav.orarend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RoomResourceImpl implements RoomResource {

    @Autowired
    private RoomService roomService;

    @Override
    public ResponseEntity<String> createRoom(Map<String, String> roomData) {
        try{
            return roomService.createRoom(roomData);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().body("Error");
    }
}
