package com.miroslav.orarend.serviceImpl;

import com.miroslav.orarend.pojo.User;
import com.miroslav.orarend.repository.UserRepository;
import com.miroslav.orarend.service.UserService;
import com.miroslav.orarend.serviceImpl.validator.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class UserServiceImpl implements UserService {


    private final UserValidator userValidator;

    private final UserRepository userRepository;

    public UserServiceImpl(UserValidator userValidator, UserRepository userRepository) {
        this.userValidator = userValidator;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<String> signUp(Map<String, String> userData) {
        if (userValidator.validateSignUpMap(userData)) {
            User user = userRepository.findByEmail(userData.get("email"));
            if (user == null) {
                userRepository.save(createUserFromMap(userData));
                return new ResponseEntity<>("Successfully registered", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Already Registered", HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>("Invalid Data", HttpStatus.BAD_REQUEST);
    }

    private User createUserFromMap(Map<String, String> userData) {
        User user = new User();
        user.setUsername(userData.get("username"));
        user.setEmail(userData.get("email"));
        user.setPassword(userData.get("password"));
        return user;
    }
}
