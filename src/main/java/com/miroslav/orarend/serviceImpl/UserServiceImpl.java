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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    // 🔹 Helper metódus – a bejelentkezett user lekérése
    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
    }

    // 🔹 Teljes update (PUT)
    @Override
    public ResponseEntity<String> updateUser(Long userId, UserInputDTO userInputDTO) {
        User currentUser = getCurrentUser();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // csak a saját adatait módosíthatja
        if (!user.getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to update this user");
        }

        user.setUsername(userInputDTO.getUsername());
        user.setEmail(userInputDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userInputDTO.getPassword()));

        userRepository.save(user);
        return ResponseEntity.ok("User updated successfully");
    }

    // 🔹 Részleges update (PATCH)
    @Override
    public ResponseEntity<String> patchUser(Long userId, UserPatchDTO patchDTO) {
        User currentUser = getCurrentUser();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (!user.getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to modify this user");
        }

        if (patchDTO.getUsername() != null) {
            user.setUsername(patchDTO.getUsername());
        }
        if (patchDTO.getEmail() != null) {
            user.setEmail(patchDTO.getEmail());
        }
        if (patchDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(patchDTO.getPassword()));
        }

        userRepository.save(user);
        return ResponseEntity.ok("User patched successfully");
    }

    // 🔹 Egy user lekérése ID alapján
    @Override
    public ResponseEntity<UserOutputDTO> getUser(Long userId) {
        User currentUser = getCurrentUser();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (!user.getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to access this user");
        }

        UserOutputDTO userOutputDTO = userMapper.toOutputDTO(user);
        return ResponseEntity.ok(userOutputDTO);
    }

    // 🔹 Jelszóváltás
    @Override
    public ResponseEntity<AuthenticationResponse> changePassword(UserChangePasswordDTO inputDTO) {
        User user = getCurrentUser();

        // ellenőrzés: régi jelszó helyes?
        if (!passwordEncoder.matches(inputDTO.getOldPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Old password is incorrect");
        }

        // új jelszó különbözik a régitől?
        if (passwordEncoder.matches(inputDTO.getNewPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password must be different from old password");
        }

        user.setPassword(passwordEncoder.encode(inputDTO.getNewPassword()));
        userRepository.save(user);

        // e-mail értesítés
        emailService.sendEmail(
                user.getEmail(),
                "Jelszó sikeresen megváltoztatva",
                "Kedves " + user.getUniqueName() + ",\n\nA jelszavad sikeresen meg lett változtatva."
        );

        // új JWT token generálása
        String newToken = jwtService.generateToken(user);
        return ResponseEntity.ok(new AuthenticationResponse(newToken));
    }
}
