package com.miroslav.orarend.authentication;

import com.miroslav.orarend.authentication.authDTOs.ForgotPasswordDTO;
import com.miroslav.orarend.authentication.authDTOs.ResetPasswordDTO;
import com.miroslav.orarend.authentication.entities.AuthenticationRequest;
import com.miroslav.orarend.authentication.entities.AuthenticationResponse;
import com.miroslav.orarend.dto.input.UserInputDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class AuthenticationResource {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody UserInputDTO inputDTO) {
        AuthenticationResponse response = authenticationService.register(inputDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            return ResponseEntity.ok(authenticationService.authenticate(request));
        }catch (Exception e) {
            log.error(e.getMessage());
            return (ResponseEntity<AuthenticationResponse>) ResponseEntity.internalServerError();
        }
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordDTO inputDTO) {
        try {
            return ResponseEntity.ok(String.valueOf(authenticationService.forgotPassword(inputDTO)));
        }catch (Exception e) {
            log.error(e.getMessage());
            return (ResponseEntity<String>) ResponseEntity.internalServerError();
        }
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordDTO inputDTO) {
        try {
            return ResponseEntity.ok(String.valueOf(authenticationService.resetPassword(inputDTO)));
        }catch (Exception e) {
            log.error(e.getMessage());
            return (ResponseEntity<String>) ResponseEntity.internalServerError();
        }
    }
}
