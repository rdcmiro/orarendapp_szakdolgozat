package com.miroslav.orarend.service;


import com.miroslav.orarend.dto.input.EmailRequestDTO;
import org.springframework.http.ResponseEntity;

public interface EmailService {

    ResponseEntity<String> sendToUser(EmailRequestDTO dto);

}
