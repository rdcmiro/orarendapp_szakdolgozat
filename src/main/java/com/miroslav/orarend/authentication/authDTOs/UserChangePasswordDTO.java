package com.miroslav.orarend.authentication.authDTOs;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserChangePasswordDTO {

    @NotEmpty
    private String oldPassword;
    @NotEmpty
    private String newPassword;
}
