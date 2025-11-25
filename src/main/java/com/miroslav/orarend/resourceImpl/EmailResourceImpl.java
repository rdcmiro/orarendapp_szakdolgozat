package com.miroslav.orarend.resourceImpl;

import com.miroslav.orarend.resource.EmailResource;
import com.miroslav.orarend.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EmailResourceImpl implements EmailResource {

    private final EmailService emailService;

    @Override
    public ResponseEntity<String> sendToUser(String body, String subject) {
        try{
            return emailService.sendToUser(body, subject);
        }catch (Exception e) {
            log.warn("Hiba a tartalom elküldésekor" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
