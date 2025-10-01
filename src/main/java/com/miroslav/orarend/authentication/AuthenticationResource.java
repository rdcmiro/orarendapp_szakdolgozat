package com.miroslav.orarend.authentication;

import com.miroslav.orarend.dto.input.UserInputDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationResource {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserInputDTO inputDTO) {
        return ResponseEntity.ok(authenticationService.register(inputDTO));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/changePassword")
    public ResponseEntity<AuthenticationResponse> changePassword(@RequestBody UserChangePasswordDTO inputDTO) {
        return ResponseEntity.ok(authenticationService.changePassword(inputDTO));
    }
}
