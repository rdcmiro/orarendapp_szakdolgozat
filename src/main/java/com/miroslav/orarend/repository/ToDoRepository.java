package com.miroslav.orarend.repository;

import com.miroslav.orarend.pojo.ToDo;
import com.miroslav.orarend.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoRepository extends JpaRepository<ToDo, Long> {

    ToDo findByUser(User user);
}
