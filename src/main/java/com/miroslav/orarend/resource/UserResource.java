package com.miroslav.orarend.resource;

import com.miroslav.orarend.authentication.authDTOs.UserChangePasswordDTO;
import com.miroslav.orarend.authentication.entities.AuthenticationResponse;
import com.miroslav.orarend.dto.input.UserInputDTO;
import com.miroslav.orarend.dto.output.UserOutputDTO;
import com.miroslav.orarend.dto.patch.UserPatchDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/users")
public interface UserResource {

    @PutMapping("/update/{userId}")
    ResponseEntity<String> updateUser(@PathVariable Long userId, @Valid @RequestBody UserInputDTO userInputDTO);

    @PatchMapping("/patch/{userId}")
    ResponseEntity<String> patchUser(@PathVariable Long userId, @RequestBody UserPatchDTO userPatchDTO);

    @GetMapping("/getUser/{userId}")
    ResponseEntity<UserOutputDTO> getUser(@PathVariable Long userId);

    @PostMapping("/changePassword")
    ResponseEntity<AuthenticationResponse> changePassword(@RequestBody UserChangePasswordDTO inputDTO);
}
