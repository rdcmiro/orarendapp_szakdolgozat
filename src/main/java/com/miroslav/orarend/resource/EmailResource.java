package com.miroslav.orarend.resource;

import com.miroslav.orarend.dto.input.EmailRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/sendOut")
public interface EmailResource {

    @PostMapping("/sendToUser")
    ResponseEntity<String> sendToUser(@RequestBody EmailRequestDTO dto);

}
