package com.miroslav.orarend.serviceImpl;

import com.miroslav.orarend.dto.UserInputDTO;
import com.miroslav.orarend.mapper.UserMapper;
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

    private final UserMapper userMapper;

    public UserServiceImpl(UserValidator userValidator, UserRepository userRepository, UserMapper userMapper) {
        this.userValidator = userValidator;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public ResponseEntity<String> signUp(UserInputDTO userInputDTO) {
        User user = userMapper.toEntity(userInputDTO);
        if(userRepository.existsByEmail(userInputDTO.getEmail())){
            return new ResponseEntity<>("Email has already been registered", HttpStatus.CONFLICT);
        }
        if (userRepository.existsByUsername(userInputDTO.getUsername())) {
            return new ResponseEntity<>("A user with the same username has already been registered", HttpStatus.CONFLICT);
        }
        userRepository.save(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    private User createUserFromMap(Map<String, String> userData) {
        User user = new User();
        user.setUsername(userData.get("username"));
        user.setEmail(userData.get("email"));
        user.setPassword(userData.get("password"));
        return user;
    }
}
