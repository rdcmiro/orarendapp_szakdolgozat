package com.miroslav.orarend.service;

import com.miroslav.orarend.dto.UserInputDTO;
import com.miroslav.orarend.dto.UserPatchDTO;
import org.springframework.http.ResponseEntity;

import java.util.Map;


public interface UserService {

    ResponseEntity<String> signUp(UserInputDTO userInputDTO);

    ResponseEntity<String> updateUser(Long userId, UserInputDTO userInputDTO);

    ResponseEntity<String> patchUser(Long userId, UserPatchDTO userPatchDTO);
}
