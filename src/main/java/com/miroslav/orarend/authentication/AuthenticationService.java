package com.miroslav.orarend.authentication;


import com.miroslav.orarend.dto.input.UserInputDTO;
import com.miroslav.orarend.pojo.Role;
import com.miroslav.orarend.pojo.User;
import com.miroslav.orarend.repository.UserRepository;
import com.miroslav.orarend.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final EmailService emailService;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

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
        try {
            emailService.sendEmail(
                    user.getEmail(),
                    "Sikeres regisztráció",
                    "\nKedves " + user.getUniqueName() + ", örömmel üdvözöljük az "
                    + "Okos órarend szolgáltatásban."
            );
            System.out.println("✅ Email küldve!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        userRepository.save(user);
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
        System.out.printf("JWT Token: %s", jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse changePassword(UserChangePasswordDTO inputDTO) {
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
            return new AuthenticationResponse(newToken);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Hiba történt a jelszó változtatás közben");
        }
    }

}
