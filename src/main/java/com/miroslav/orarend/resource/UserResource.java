package com.miroslav.orarend.resource;

import com.miroslav.orarend.dto.UserInputDTO;
import com.miroslav.orarend.dto.UserPatchDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/users")
public interface UserResource {

    @PostMapping("/signup")
    ResponseEntity<String> signUp(@RequestBody UserInputDTO userInputDTO);

    @PutMapping("/update/{userId}")
    ResponseEntity<String> updateUser(@PathVariable Long userId, @Valid @RequestBody UserInputDTO userInputDTO);

    @PatchMapping("/patch/{userId}")
    ResponseEntity<String> patchUser(@PathVariable Long userId, @RequestBody UserPatchDTO userPatchDTO);

}
