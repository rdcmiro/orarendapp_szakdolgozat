package com.miroslav.orarend.authentication;


import com.miroslav.orarend.authentication.authDTOs.ForgotPasswordDTO;
import com.miroslav.orarend.authentication.authDTOs.ResetPasswordDTO;
import com.miroslav.orarend.authentication.entities.AuthenticationRequest;
import com.miroslav.orarend.authentication.entities.AuthenticationResponse;
import com.miroslav.orarend.authentication.entities.PasswordResetToken;
import com.miroslav.orarend.dto.input.UserInputDTO;
import com.miroslav.orarend.pojo.Role;
import com.miroslav.orarend.pojo.User;
import com.miroslav.orarend.repository.PasswordResetTokenRepository;
import com.miroslav.orarend.repository.UserRepository;
import com.miroslav.orarend.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {

    private final EmailService emailService;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final PasswordResetTokenRepository  passwordResetTokenRepository;


    public AuthenticationResponse register(UserInputDTO inputDTO) {
        var user = User.builder()
                .username(inputDTO.getUsername())
                .password(passwordEncoder.encode(inputDTO.getPassword()))
                .email(inputDTO.getEmail())
                .role(Role.USER)
                .build();
        if(userRepository.existsByEmail(user.getEmail())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email has already been registered");
        }
        if (userRepository.existsByUsername(user.getUniqueName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username has already been registered");
        }
        userRepository.save(user);
        try {
            emailService.sendEmail(
                    user.getEmail(),
                    "Sikeres regisztráció",
                    "\nKedves " + user.getUniqueName() + ", örömmel üdvözöljük az "
                            + "Okos órarend szolgáltatásban."
            );
        } catch (Exception e) {
            log.warn("Az üdvőzlő email küldése sikertelen volt.");
        }
        var jwtToken = jwtService.generateToken(user);
        System.out.printf("JWT Token: %s", jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Transactional
    public ResponseEntity<String> forgotPassword(ForgotPasswordDTO inputDTO) {
        try{
            Optional<User> user = userRepository.findByEmail(inputDTO.getEmail());

            if(user.isPresent()){
                User presentUser = user.get();

                if(passwordResetTokenRepository.existsByUser(presentUser)){
                    passwordResetTokenRepository.deleteByUser(presentUser);
                }

                String resetToken = UUID.randomUUID().toString();

                String encodedToken = passwordEncoder.encode(resetToken);

                PasswordResetToken passwordResetToken = new PasswordResetToken();
                passwordResetToken.setTokenHash(encodedToken);
                passwordResetToken.setUser(presentUser);

                passwordResetTokenRepository.save(passwordResetToken);

                emailService.sendEmail(
                        presentUser.getEmail(),
                        "Jelszó visszaállítás",
                        "Kedves " + presentUser.getUniqueName() +
                                ",\n\nItt van a kódod a jelszavad visszaállításához: " + resetToken);
            }
            return ResponseEntity.ok("If an account exists with that email, we've sent you reset instructions");
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> resetPassword(ResetPasswordDTO inputDTO) {
        try {
            User user = userRepository.findByEmail(inputDTO.getEmail())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

            PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByUser(user)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No reset token found"));

            if (passwordResetToken.isExpired()) {
                log.warn("A felhasználó a következő email-címmel: "+  user.getUniqueName() +
                        "lejárt tokent adott meg");
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Reset token expired");

            }

            if (!passwordEncoder.matches(inputDTO.getToken(), passwordResetToken.getTokenHash())) {
                log.warn("A felhasználó a következő email-címmel: "+  user.getUniqueName() +
                        "Hibás tokent adott meg");
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
            }

            user.setPassword(passwordEncoder.encode(inputDTO.getNewPassword()));
            userRepository.save(user);

            passwordResetTokenRepository.delete(passwordResetToken);

            emailService.sendEmail(
                    user.getEmail(),
                    "Jelszó visszaállítás",
                    "Kedves " + user.getUniqueName() + ",\n\nA jelszavad sikeresen meg lett változtatva."
            );

            return ResponseEntity.ok("Jelszó megváltoztatva");
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            log.error("Nem várt hiba");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error");
        }
    }

}
