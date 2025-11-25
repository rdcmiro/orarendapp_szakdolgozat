package com.miroslav.orarend.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/assist")
public interface AiResource {

    @PostMapping("/todo")
    ResponseEntity<String> todoAssist();

}
