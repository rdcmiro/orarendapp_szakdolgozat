package com.miroslav.orarend.serviceImpl.validator;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserValidator {

    public boolean validateSignUpMap(Map<String, String> userData) {
        return userData.containsKey("username") && userData.containsKey("email")
                && userData.containsKey("password");
    }
}
