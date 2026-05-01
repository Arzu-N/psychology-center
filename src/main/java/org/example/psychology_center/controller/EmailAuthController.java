package org.example.psychology_center.controller;

import lombok.RequiredArgsConstructor;
import org.example.psychology_center.dto.request.RegisterRequest;
import org.example.psychology_center.service.AuthService;
import org.example.psychology_center.service.EmailAuthService;
import org.example.psychology_center.service.OtpService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth/email")
@RequiredArgsConstructor
public class EmailAuthController {

    private final OtpService otpService;
    private final EmailAuthService service;

    @PostMapping("/send-otp")
    public String sendOtp(@RequestParam String email) {
        otpService.sendOtp(email);
        return "OTP göndərildi";
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String email,
                            @RequestParam String code) {

        otpService.verifyOtp(email, code);
        return "Təsdiqləndi";
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {

        service.register(request);
        return "Qeydiyyat uğurlu";
    }
}