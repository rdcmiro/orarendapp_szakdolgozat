package com.miroslav.orarend.resource;


import com.miroslav.orarend.dto.RoomInputDTO;
import com.miroslav.orarend.dto.RoomOutputDTO;
import com.miroslav.orarend.dto.RoomPatchDTO;
import com.miroslav.orarend.pojo.Room;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/rooms")
public interface RoomResource {

    @PostMapping("/create")
    ResponseEntity<String> createRoom(@RequestBody RoomInputDTO roomInputDTO);

    @PutMapping("/update/{roomId}")
    ResponseEntity<String> updateRoom(@PathVariable Long roomId, @RequestBody RoomInputDTO roomInputDTO);

    @PatchMapping("/patch/{lessonId}")
    ResponseEntity<String> patchRoom(@PathVariable Long lessonId, @RequestBody RoomPatchDTO roomPatchDTO);

    @GetMapping("/getRoom/{roomId}")
    ResponseEntity<RoomOutputDTO> getRoom(@PathVariable Long roomId);

    @DeleteMapping("/deleteRoom/{roomId}")
    ResponseEntity<String> deleteRoom(@PathVariable Long roomId);
}
