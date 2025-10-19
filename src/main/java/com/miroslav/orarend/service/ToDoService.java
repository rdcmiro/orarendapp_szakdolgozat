package com.miroslav.orarend.service;

import com.miroslav.orarend.dto.input.ToDoInputDTO;
import com.miroslav.orarend.dto.output.ToDoOutputDTO;
import com.miroslav.orarend.dto.patch.ToDoPatchDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ToDoService {

    ResponseEntity<String> createToDo(ToDoInputDTO toDoInputDTO);

    ResponseEntity<String> updateToDo(Long id,ToDoInputDTO toDoInputDTO);

    ResponseEntity<String> patchToDo(Long toDoId, ToDoPatchDTO toDoPatchDTO);

    ResponseEntity<ToDoOutputDTO> getToDo(Long toDoId);

    ResponseEntity<String> deleteToDo(Long toDoId);

    ResponseEntity<List<ToDoOutputDTO>> getAllUserToDos();
}
