package com.miroslav.orarend.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;


public interface UserService {

    ResponseEntity<String> signUp(Map<String, String> userData);
}
