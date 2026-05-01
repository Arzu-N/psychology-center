package org.example.psychology_center.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.psychology_center.dto.request.LoginRequestDto;
import org.example.psychology_center.dto.request.RefreshRequest;
import org.example.psychology_center.dto.request.RegisterRequest;
import org.example.psychology_center.dto.response.AuthResponse;
import org.example.psychology_center.service.AuthService;
import org.example.psychology_center.util.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/api/v1")
public class AuthController {
private final AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {

        authService.register(request);
        return "Qeydiyyat uğurlu";
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse>login(@RequestBody @Valid LoginRequestDto dto){
        return ResponseEntity.ok(authService.login(dto));
    }


    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestBody RefreshRequest request) {
        return authService.refreshToken(request.getRefreshToken());
    }

    @PutMapping("/users/{id}/role")
    public ResponseEntity<String> changeRole(
            @PathVariable Long id,
            @RequestParam Role role) {
        authService.changeRole(id, role);
        return ResponseEntity.ok("Role updated");
    }

}
