package com.miroslav.orarend.service;


import org.springframework.http.ResponseEntity;

public interface EmailService {

    ResponseEntity<String> sendToUser(String body, String subject);

}
