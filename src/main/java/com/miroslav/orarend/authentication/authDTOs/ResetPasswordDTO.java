package com.miroslav.orarend.authentication.authDTOs;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class ResetPasswordDTO {

    @Email
    private String email;

    private String token;

    private String newPassword;
}
