package org.example.psychology_center.controller;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.psychology_center.dto.request.LoginRequestDto;
import org.example.psychology_center.dto.request.RefreshRequest;
import org.example.psychology_center.dto.request.RegisterRequest;
import org.example.psychology_center.dto.response.AuthResponse;
import org.example.psychology_center.service.AuthService;
import org.example.psychology_center.service.OtpService;
import org.example.psychology_center.util.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/api/v1")
public class AuthController {

    private final AuthService authService;
    private final OtpService otpService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("Qeydiyyat uğurlu");
    }
    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestParam String email,
                                            @RequestParam String code) {
        otpService.verifyOtp(email, code);
        return ResponseEntity.ok("Email təsdiqləndi");
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequestDto dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshRequest request) {
        return ResponseEntity.ok(
                authService.refreshToken(request.getRefreshToken())
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @RequestBody RefreshRequest request
    ) {

        authService.logout(request.getRefreshToken());

        return ResponseEntity.ok("Logged out successfully");
    }


    @PutMapping("/users/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> changeRole(
            @PathVariable Long id,
            @RequestParam Role role) {

        authService.changeRole(id, role);
        return ResponseEntity.ok("Role updated");
    }
}