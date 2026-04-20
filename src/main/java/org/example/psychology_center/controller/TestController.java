package org.example.psychology_center.controller;

import lombok.RequiredArgsConstructor;
import org.example.psychology_center.dto.request.SubmitDto;
import org.example.psychology_center.service.TestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test/api/v1")
public class TestController {
private final TestService service;
    @PostMapping("/submit")
    public ResponseEntity<Integer>submitTest(@RequestBody SubmitDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.submitTest(dto));
    }
}
