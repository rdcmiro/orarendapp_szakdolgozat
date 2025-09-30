package com.miroslav.orarend.service;

import com.miroslav.orarend.dto.UserInputDTO;
import com.miroslav.orarend.dto.UserOutputDTO;
import com.miroslav.orarend.dto.UserPatchDTO;
import org.springframework.http.ResponseEntity;


public interface UserService {

    ResponseEntity<String> updateUser(Long userId, UserInputDTO userInputDTO);

    ResponseEntity<String> patchUser(Long userId, UserPatchDTO userPatchDTO);

    ResponseEntity<UserOutputDTO> getUser(Long userId);

}
