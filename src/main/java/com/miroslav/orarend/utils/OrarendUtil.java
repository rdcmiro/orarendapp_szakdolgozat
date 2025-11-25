package com.miroslav.orarend.utils;

import com.miroslav.orarend.dto.output.ToDoOutputDTO;
import com.miroslav.orarend.mapper.ToDoMapper;
import com.miroslav.orarend.pojo.ToDo;
import com.miroslav.orarend.pojo.User;
import com.miroslav.orarend.repository.ToDoRepository;
import com.miroslav.orarend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrarendUtil {

    private final UserRepository userRepository;
    private final ToDoRepository toDoRepository;
    private final ToDoMapper toDoMapper;
    private final JavaMailSender mailSender;

    public User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }

        return userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
    }

    public List<ToDoOutputDTO> getAllToDosForPlan(User user){
        List<ToDo> toDoListByUser = toDoRepository.findAllByUser(user);
        return toDoListByUser.stream()
                .map(toDoMapper::toOutputDto)
                .toList();
    }

    @Async
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("orarendalkalmazasszakdolgozat@gmail.com");
        mailSender.send(message);
    }
}
