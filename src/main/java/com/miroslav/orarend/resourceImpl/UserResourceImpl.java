package com.miroslav.orarend.resourceImpl;

import com.miroslav.orarend.resource.UserResource;
import com.miroslav.orarend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserResourceImpl implements UserResource {

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> userData) {
        try{
            return userService.signUp(userData);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
