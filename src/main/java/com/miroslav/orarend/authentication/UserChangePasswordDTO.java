package com.miroslav.orarend.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserChangePasswordDTO {

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String oldPassword;
    @NotEmpty
    private String newPassword;
}
