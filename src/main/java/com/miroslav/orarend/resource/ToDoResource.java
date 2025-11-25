package com.miroslav.orarend.resource;

import com.miroslav.orarend.dto.input.ToDoInputDTO;
import com.miroslav.orarend.dto.output.ToDoOutputDTO;
import com.miroslav.orarend.dto.patch.ToDoPatchDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/toDo")
public interface ToDoResource {

    @PostMapping("/create")
    ResponseEntity<String> createToDo(@RequestBody ToDoInputDTO toDoInputDTO);

    @PutMapping("/update/{toDoId}")
    ResponseEntity<String> updateToDo(@PathVariable Long toDoId, @RequestBody ToDoInputDTO toDoInputDTO);

    @PatchMapping("/patch/{toDoId}")
    ResponseEntity<String> patchToDo(@PathVariable Long toDoId, @RequestBody ToDoPatchDTO toDoPatchDTO);

    @GetMapping("/get/{toDoId}")
    ResponseEntity<ToDoOutputDTO> getToDo(@PathVariable Long toDoId);

    @DeleteMapping("/delete/{toDoId}")
    ResponseEntity<String> deleteLesson(@PathVariable Long toDoId);

    @GetMapping("/allUserToDos")
    ResponseEntity<List<ToDoOutputDTO>> getAllUserToDos();
}
