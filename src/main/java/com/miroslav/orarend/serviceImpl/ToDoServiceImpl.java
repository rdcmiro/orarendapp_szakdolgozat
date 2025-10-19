package com.miroslav.orarend.serviceImpl;

import com.miroslav.orarend.dto.input.ToDoInputDTO;
import com.miroslav.orarend.dto.output.ToDoOutputDTO;
import com.miroslav.orarend.dto.patch.ToDoPatchDTO;
import com.miroslav.orarend.mapper.ToDoMapper;
import com.miroslav.orarend.pojo.ToDo;
import com.miroslav.orarend.pojo.User;
import com.miroslav.orarend.repository.ToDoRepository;
import com.miroslav.orarend.repository.UserRepository;
import com.miroslav.orarend.service.ToDoService;
import com.miroslav.orarend.serviceImpl.validator.ToDoValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ToDoServiceImpl implements ToDoService {

    private final ToDoRepository toDoRepository;
    private final ToDoMapper toDoMapper;
    private final UserRepository userRepository;
    private final ToDoValidator toDoValidator;

    @Override
    public ResponseEntity<String> createToDo(ToDoInputDTO toDoInputDTO) {
        ToDo input = toDoMapper.toEntity(toDoInputDTO);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
        input.setUser(user);
        if(!toDoValidator.doesToDoAlreadyExistsByTitle(input.getTitle(), user))
        {
            toDoRepository.save(input);
            return ResponseEntity.ok("Successfully created to do "+input.getTitle());
        }else
        {
            return new ResponseEntity<>("ToDo already exists with title "+input.getTitle(), HttpStatus.CONFLICT);
        }
    }

    @Override
    public ResponseEntity<String> updateToDo(Long toDoId, ToDoInputDTO toDoInputDTO) {
        ToDo todo = toDoRepository.findById(toDoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ToDo not found"));

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        if (!todo.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to edit this ToDo");
        }

        boolean titleExists = toDoRepository.existsByTitleAndUser(toDoInputDTO.getTitle(), user);
        boolean isTitleChanged = !todo.getTitle().equals(toDoInputDTO.getTitle());
        if (titleExists && isTitleChanged) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Another ToDo already has this title");
        }

        todo.setTitle(toDoInputDTO.getTitle());
        todo.setDescription(toDoInputDTO.getDescription());
        todo.setDueTime(toDoInputDTO.getDueTime());
        todo.setIsItDone(toDoInputDTO.getIsItDone());

        toDoRepository.save(todo);

        return ResponseEntity.ok("Successfully updated ToDo: " + todo.getTitle());
    }


    @Override
    public ResponseEntity<String> patchToDo(Long id, ToDoPatchDTO toDoPatchDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        ToDo todo = toDoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ToDo not found"));

        if (!todo.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to modify this ToDo");
        }

        if (toDoPatchDTO.getTitle() != null && !toDoPatchDTO.getTitle().equals(todo.getTitle())) {
            boolean exists = toDoRepository.existsByTitleAndUser(toDoPatchDTO.getTitle(), user);
            if (exists) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Another ToDo already has this title");
            }
            todo.setTitle(toDoPatchDTO.getTitle());
        }

        if (toDoPatchDTO.getDescription() != null) {
            todo.setDescription(toDoPatchDTO.getDescription());
        }

        if (toDoPatchDTO.getDueTime() != null) {
            todo.setDueTime(toDoPatchDTO.getDueTime());
        }

        if (toDoPatchDTO.getIsItDone() != null) {
            todo.setIsItDone(toDoPatchDTO.getIsItDone());
        }

        toDoRepository.save(todo);
        return ResponseEntity.ok("Successfully patched ToDo: " + todo.getTitle());
    }

    @Override
    public ResponseEntity<ToDoOutputDTO> getToDo(Long toDoId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        ToDo todo = toDoRepository.findById(toDoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ToDo not found"));

        if (!todo.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to access this ToDo");
        }

        ToDoOutputDTO result = toDoMapper.toOutputDto(todo);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<String> deleteToDo(Long toDoId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        ToDo todo = toDoRepository.findById(toDoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ToDo not found"));

        if (!todo.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to delete this ToDo");
        }

        toDoRepository.delete(todo);
        return ResponseEntity.ok("Successfully deleted ToDo: " + todo.getTitle());
    }

    @Override
    public ResponseEntity<List<ToDoOutputDTO>> getAllUserToDos() {
        User currentUser = userRepository
                .findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        List<ToDo> toDoListByUser = toDoRepository.findAllByUser(currentUser);
        List<ToDoOutputDTO> result = toDoListByUser.stream()
                .map(toDoMapper::toOutputDto)
                .toList();

        return ResponseEntity.ok(result);
    }
}
