package com.miroslav.orarend.repository;

import com.miroslav.orarend.pojo.ToDo;
import com.miroslav.orarend.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToDoRepository extends JpaRepository<ToDo, Long> {

    ToDo findByUser(User user);

    boolean existsByTitleAndUser(String title, User user);

    List<ToDo> findAllByUser(User user);
}
