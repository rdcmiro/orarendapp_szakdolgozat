package com.miroslav.orarend.authentication.authDTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ForgotPasswordDTO {

    @NotEmpty
    @Email
    private String email;
}
