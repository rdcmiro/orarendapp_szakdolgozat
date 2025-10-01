package com.miroslav.orarend.serviceImpl;

import com.miroslav.orarend.dto.input.UserInputDTO;
import com.miroslav.orarend.dto.output.UserOutputDTO;
import com.miroslav.orarend.dto.patch.UserPatchDTO;
import com.miroslav.orarend.mapper.UserMapper;
import com.miroslav.orarend.pojo.User;
import com.miroslav.orarend.repository.UserRepository;
import com.miroslav.orarend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    private final UserMapper userMapper;


    @Override
    public ResponseEntity<String> updateUser(Long userId, UserInputDTO userInputDTO) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()){
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        User user = optionalUser.get();

        user.setUsername(userInputDTO.getUsername());
        user.setEmail(userInputDTO.getEmail());
        user.setPassword(userInputDTO.getPassword());
        userRepository.save(user);
        return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> patchUser (Long userId, UserPatchDTO patchDTO) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()){
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        User user = optionalUser.get();

        if(patchDTO.getUsername() != null){
            user.setUsername(patchDTO.getUsername());
        }
        if(patchDTO.getEmail() != null){
            user.setEmail(patchDTO.getEmail());
        }
        if(patchDTO.getPassword() != null){
            user.setPassword(patchDTO.getPassword());
        }
        userRepository.save(user);
        return new ResponseEntity<>("User patched successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserOutputDTO> getUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserOutputDTO userOutputDTO = userMapper.toOutputDTO(optionalUser.get());
        return new ResponseEntity<>(userOutputDTO, HttpStatus.OK);
    }

}
