package com.miroslav.orarend.resource;

import com.miroslav.orarend.dto.UserInputDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping("/users")
public interface UserResource {

    @PostMapping("/signup")
    ResponseEntity<String> signUp(@RequestBody UserInputDTO userInputDTO);

}
