package org.example.psychology_center.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.psychology_center.dto.request.RefreshRequest;
import org.example.psychology_center.dto.request.UserRequestDto;
import org.example.psychology_center.dto.response.AuthResponse;
import org.example.psychology_center.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/api/v1")
public class AuthController {
private final AuthService service;
    @PostMapping("/register")
    public ResponseEntity<Void>register(@RequestBody @Valid UserRequestDto dto){
        service.register(dto);
       return ResponseEntity.noContent().build();
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse>login(@RequestBody @Valid UserRequestDto dto){
        return ResponseEntity.ok(service.login(dto));
    }


    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestBody RefreshRequest request) {
        return service.refreshToken(request.getRefreshToken());
    }

}
