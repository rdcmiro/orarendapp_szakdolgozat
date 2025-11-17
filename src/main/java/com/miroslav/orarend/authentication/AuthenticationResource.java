package com.miroslav.orarend.authentication;

import com.miroslav.orarend.authentication.authDTOs.ForgotPasswordDTO;
import com.miroslav.orarend.authentication.authDTOs.ResetPasswordDTO;
import com.miroslav.orarend.authentication.entities.AuthenticationRequest;
import com.miroslav.orarend.authentication.entities.AuthenticationResponse;
import com.miroslav.orarend.dto.input.UserInputDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationResource {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserInputDTO inputDTO) {
        try {
            return ResponseEntity.ok(authenticationService.register(inputDTO));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return (ResponseEntity<AuthenticationResponse>) ResponseEntity.internalServerError();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            return ResponseEntity.ok(authenticationService.authenticate(request));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return (ResponseEntity<AuthenticationResponse>) ResponseEntity.internalServerError();
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordDTO inputDTO) {
        try {
            return ResponseEntity.ok(String.valueOf(authenticationService.forgotPassword(inputDTO)));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return (ResponseEntity<String>) ResponseEntity.internalServerError();
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordDTO inputDTO) {
        try {
            return ResponseEntity.ok(String.valueOf(authenticationService.resetPassword(inputDTO)));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return (ResponseEntity<String>) ResponseEntity.internalServerError();
    }
}
