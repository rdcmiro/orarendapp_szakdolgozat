package com.miroslav.orarend.resource;

import com.miroslav.orarend.dto.output.ToDoOutputDTO;
import com.miroslav.orarend.dto.patch.ToDoPatchDTO;
import org.springframework.http.ResponseEntity;
import com.miroslav.orarend.dto.input.ToDoInputDTO;
import org.springframework.web.bind.annotation.*;

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
