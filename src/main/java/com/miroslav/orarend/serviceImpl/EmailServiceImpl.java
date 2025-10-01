package com.miroslav.orarend.serviceImpl;

import com.miroslav.orarend.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Async
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("orarendalkalmazasszakdolgozat@gmail.com");

        System.out.println("📧 Sending mail to " + to + " with subject: " + subject);

        mailSender.send(message); // <-- ez a legfontosabb!
    }
}
