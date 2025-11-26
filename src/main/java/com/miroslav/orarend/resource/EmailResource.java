package com.miroslav.orarend.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/sendOut")
public interface EmailResource {

    @PostMapping("/sendToUser")
    ResponseEntity<String> sendToUser(@RequestParam String body, @RequestParam String subject);

}
