package com.miroslav.orarend.service;

import com.miroslav.orarend.dto.UserInputDTO;
import org.springframework.http.ResponseEntity;

import java.util.Map;


public interface UserService {

    ResponseEntity<String> signUp(UserInputDTO userInputDTO);
}
