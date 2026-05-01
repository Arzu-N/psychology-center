package org.example.psychology_center.service;

import lombok.RequiredArgsConstructor;
import org.example.psychology_center.dao.entity.User;
import org.example.psychology_center.dao.repository.UserRepository;
import org.example.psychology_center.dto.request.RegisterRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
    @RequiredArgsConstructor
    public class EmailAuthService {

        private final OtpService otpService;
        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;

        public void register(RegisterRequest request) {

            // OTP yoxla
            if (!otpService.isVerified(request.getEmail())) {
                throw new RuntimeException("Email təsdiqlənməyib");
            }

            User user = new User();
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));

            userRepository.save(user);
        }
    }


