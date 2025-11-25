package com.miroslav.orarend.resourceImpl;

import com.miroslav.orarend.resource.AiResource;
import com.miroslav.orarend.service.AiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AiResourceImpl implements AiResource {
    
    private final AiService aiService;
    
    @Override
    public ResponseEntity<String> todoAssist() {
        try{
            String result = aiService.todoSummarize();
            return ResponseEntity.ok(result);
        }catch(Exception e){
            log.warn("todo assist failed" + e.getMessage());
            return new ResponseEntity<>("Error while generating study plan", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
