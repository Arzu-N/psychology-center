package org.example.psychology_center.service;

import lombok.RequiredArgsConstructor;
import org.example.psychology_center.dao.entity.User;
import org.example.psychology_center.dao.repository.UserRepository;

import org.example.psychology_center.exception.BaseException;
import org.example.psychology_center.exception.NotFoundException;
import org.example.psychology_center.exception.ValidationException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
@Service
@RequiredArgsConstructor
public class OtpService {

    private final EmailService emailService;
    private final UserRepository userRepository;
    private final StringRedisTemplate stringRedisTemplate;

    public void sendOtp(String email) {

        String otpKey = "otp:" + email;
        String limitKey = "otp:limit:" + email;

        stringRedisTemplate.opsForValue()
                .setIfAbsent(limitKey, "0", 10, TimeUnit.MINUTES);

        String limitStr = stringRedisTemplate.opsForValue().get(limitKey);

        int limit = (limitStr == null) ? 0 : Integer.parseInt(limitStr);

        if (limit >= 3) {
            throw new BaseException("OTP göndərmə limiti keçilib");
        }

        stringRedisTemplate.opsForValue().increment(limitKey);

        String code = String.valueOf(
                ThreadLocalRandom.current().nextInt(100000, 1000000)
        );

        stringRedisTemplate.opsForValue().set(
                otpKey,
                code,
                5,
                TimeUnit.MINUTES
        );

        emailService.sendEmail(email, "OTP Code", "Kod: " + code);
    }

    public void verifyOtp(String email, String code) {

        String otpKey = "otp:" + email;

        String savedCode = stringRedisTemplate.opsForValue().get(otpKey);

        if (savedCode == null) {
            throw new NotFoundException("OTP tapılmadı və ya vaxtı bitib");
        }

        if (!savedCode.equals(code)) {
            throw new ValidationException("Kod səhvdir");
        }

        stringRedisTemplate.delete(otpKey);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User tapılmadı"));

        user.setVerified(true);
        userRepository.save(user);
    }
}