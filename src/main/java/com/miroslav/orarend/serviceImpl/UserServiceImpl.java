package com.miroslav.orarend.serviceImpl;

import com.miroslav.orarend.authentication.JwtService;
import com.miroslav.orarend.authentication.authDTOs.UserChangePasswordDTO;
import com.miroslav.orarend.authentication.entities.AuthenticationResponse;
import com.miroslav.orarend.dto.input.UserInputDTO;
import com.miroslav.orarend.dto.output.UserOutputDTO;
import com.miroslav.orarend.dto.patch.UserPatchDTO;
import com.miroslav.orarend.mapper.UserMapper;
import com.miroslav.orarend.pojo.User;
import com.miroslav.orarend.repository.UserRepository;
import com.miroslav.orarend.service.EmailService;
import com.miroslav.orarend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final JwtService jwtService;

    private final EmailService emailService;

    private final PasswordEncoder passwordEncoder;



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

    public ResponseEntity<AuthenticationResponse> changePassword(UserChangePasswordDTO inputDTO) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

            if (!passwordEncoder.matches(inputDTO.getOldPassword(), user.getPassword())) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Old password is incorrect");
            }

            if (passwordEncoder.matches(inputDTO.getNewPassword(), user.getPassword())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password must be different from old password");
            }

            user.setPassword(passwordEncoder.encode(inputDTO.getNewPassword()));
            userRepository.save(user);

            emailService.sendEmail(
                    user.getEmail(),
                    "Jelszó sikeresen megváltoztatva",
                    "Kedves " + user.getUniqueName() + ",\n\na jelszavad sikeresen meg lett változtatva."
            );

            String newToken = jwtService.generateToken(user);
            return new ResponseEntity<>(new AuthenticationResponse(newToken), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            throw new
                    ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Hiba történt a jelszó változtatás közben");
        }
    }

}
