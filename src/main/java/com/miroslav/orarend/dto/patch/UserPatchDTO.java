package com.miroslav.orarend.dto.patch;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserPatchDTO {

    private String username;

    private String password;

    @Email
    private String email;
}
