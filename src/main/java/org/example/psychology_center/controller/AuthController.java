package org.example.psychology_center.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.psychology_center.dto.request.UserRequestDto;
import org.example.psychology_center.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/api/v1")
public class AuthController {
private final AuthService service;
    @GetMapping("/register")
    public ResponseEntity<Void>register(@RequestBody @Valid UserRequestDto dto){
        service.register(dto);
       return ResponseEntity.noContent().build();
    }
    @GetMapping("/login")
    public ResponseEntity<String>login(@RequestBody @Valid UserRequestDto dto){
        return ResponseEntity.ok(service.login(dto));
    }
}
