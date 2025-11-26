package com.miroslav.orarend.serviceImpl;

import com.miroslav.orarend.dto.input.EmailRequestDTO;
import com.miroslav.orarend.pojo.User;
import com.miroslav.orarend.service.EmailService;
import com.miroslav.orarend.utils.OrarendUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final OrarendUtil orarendUtil;

    @Override
    public ResponseEntity<String> sendToUser(EmailRequestDTO dto) {
        try {
            User user = orarendUtil.getAuthenticatedUser();
            orarendUtil.sendEmail(user.getEmail(), dto.getSubject(), dto.getBody());
            return ResponseEntity.ok("Email sent");
        } catch (Exception e) {
            log.warn(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
