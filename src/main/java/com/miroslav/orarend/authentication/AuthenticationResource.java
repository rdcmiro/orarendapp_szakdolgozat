package com.miroslav.orarend.authentication;

import com.miroslav.orarend.authentication.authDTOs.ForgotPasswordDTO;
import com.miroslav.orarend.authentication.authDTOs.ResetPasswordDTO;
import com.miroslav.orarend.authentication.entities.AuthenticationRequest;
import com.miroslav.orarend.authentication.entities.AuthenticationResponse;
import com.miroslav.orarend.dto.input.UserInputDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
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
            AuthenticationResponse response = authenticationService.authenticate(request);
            return ResponseEntity.ok(response);
        }catch (Exception e) {
            log.error("Authentication failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestBody @Valid ForgotPasswordDTO inputDTO) {
        try {
            return ResponseEntity.ok(String.valueOf(authenticationService.forgotPassword(inputDTO)));
        }catch (Exception e) {
            log.error("Error in forgot password method", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody @Valid ResetPasswordDTO inputDTO) {
        try {
            return ResponseEntity.ok(String.valueOf(authenticationService.resetPassword(inputDTO)));
        }catch (Exception e) {
            log.error("Password reset failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}
