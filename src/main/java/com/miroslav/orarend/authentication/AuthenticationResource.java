package com.miroslav.orarend.authentication;

import com.miroslav.orarend.authentication.authDTOs.ForgotPasswordDTO;
import com.miroslav.orarend.authentication.authDTOs.ResetPasswordDTO;
import com.miroslav.orarend.authentication.entities.AuthenticationRequest;
import com.miroslav.orarend.authentication.entities.AuthenticationResponse;
import com.miroslav.orarend.dto.input.UserInputDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    @PostMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordDTO inputDTO) {
        return authenticationService.forgotPassword(inputDTO);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordDTO inputDTO) {
        return authenticationService.resetPassword(inputDTO);
    }
}
