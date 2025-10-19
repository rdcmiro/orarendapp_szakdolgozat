package com.miroslav.orarend.resourceImpl;

import com.miroslav.orarend.dto.input.ToDoInputDTO;
import com.miroslav.orarend.dto.output.ToDoOutputDTO;
import com.miroslav.orarend.dto.patch.ToDoPatchDTO;
import com.miroslav.orarend.resource.ToDoResource;
import com.miroslav.orarend.service.ToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(
        origins = "http://localhost:4200",
        allowedHeaders = {"Authorization", "Content-Type"},
        methods = {
                RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.PATCH,
                RequestMethod.OPTIONS
        }
)
@RequiredArgsConstructor
public class ToDoResourceImpl implements ToDoResource {

    private final ToDoService toDoService;

    @Override
    public ResponseEntity<String> createToDo(ToDoInputDTO toDoInputDTO) {
        try {
            return toDoService.createToDo(toDoInputDTO);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateToDo(Long toDoId, ToDoInputDTO toDoInputDTO) {
        try {
            return toDoService.updateToDo(toDoId,toDoInputDTO);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> patchToDo(Long toDoId, ToDoPatchDTO toDoPatchDTO) {
        try {
            return toDoService.patchToDo(toDoId, toDoPatchDTO);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ToDoOutputDTO> getToDo(Long toDoId) {
        try {
            return toDoService.getToDo(toDoId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ToDoOutputDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteLesson(Long toDoId) {
        try {
            return toDoService.deleteToDo(toDoId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ToDoOutputDTO>> getAllUserToDos() {
        try {
            return toDoService.getAllUserToDos();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<ToDoOutputDTO>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
