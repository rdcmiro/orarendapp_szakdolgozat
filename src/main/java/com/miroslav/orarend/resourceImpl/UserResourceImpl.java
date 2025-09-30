package com.miroslav.orarend.resourceImpl;

import com.miroslav.orarend.dto.UserInputDTO;
import com.miroslav.orarend.dto.UserOutputDTO;
import com.miroslav.orarend.dto.UserPatchDTO;
import com.miroslav.orarend.resource.UserResource;
import com.miroslav.orarend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserResourceImpl implements UserResource {

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<String> updateUser(Long userId, UserInputDTO userInputDTO) {
        try {
            return userService.updateUser(userId, userInputDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> patchUser(Long userId, UserPatchDTO userPatchDTO) {
        try {
            return userService.patchUser(userId, userPatchDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<UserOutputDTO> getUser(Long userId) {
        try {
            return userService.getUser(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
