package org.example.psychology_center.service;

import lombok.RequiredArgsConstructor;
import org.example.psychology_center.dao.entity.Otp;
import org.example.psychology_center.dao.entity.User;
import org.example.psychology_center.dao.repository.OtpRepository;
import org.example.psychology_center.dao.repository.UserRepository;
import org.example.psychology_center.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final OtpRepository otpRepository;
    private final EmailService emailService;
    private final UserRepository userRepository;

    public void sendOtp(String email) {

        String code = String.valueOf(new Random().nextInt(900000) + 100000);

        Otp otp = otpRepository.findByEmail(email)
                .orElse(new Otp());

        otp.setEmail(email);
        otp.setCode(code);
        otp.setExpiryDate(LocalDateTime.now().plusMinutes(5));
        otp.setVerified(false);

        otpRepository.save(otp);

        emailService.sendEmail(email, "OTP Code", "Kod: " + code);
    }

    public void verifyOtp(String email, String code) {

        Otp otp = otpRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("OTP tapılmadı"));

        if (otp.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Kodun vaxtı bitib");
        }

        if (!otp.getCode().equals(code)) {
            throw new RuntimeException("Kod səhvdir");
        }

        otp.setVerified(true);
        otpRepository.save(otp);

        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("user not found"));
        user.setVerified(true);
        userRepository.save(user);


    }

    public boolean isVerified(String email) {

        Otp otp = otpRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("OTP yoxdur"));

        return otp.isVerified();
    }
}