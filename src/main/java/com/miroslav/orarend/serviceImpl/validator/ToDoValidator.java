package com.miroslav.orarend.serviceImpl.validator;

import com.miroslav.orarend.pojo.User;
import com.miroslav.orarend.repository.ToDoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ToDoValidator {

    private final ToDoRepository toDoRepository;

    public boolean doesToDoAlreadyExistsByTitle(String title, User user) {
        return toDoRepository.existsByTitleAndUser(title, user);
    }
}
