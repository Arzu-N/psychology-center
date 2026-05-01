package org.example.psychology_center.service;

import lombok.RequiredArgsConstructor;
import org.example.psychology_center.dao.entity.Otp;
import org.example.psychology_center.dao.entity.User;
import org.example.psychology_center.dao.repository.OtpRepository;
import org.example.psychology_center.dao.repository.UserRepository;
import org.example.psychology_center.exception.NotFoundException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final OtpRepository otpRepository;
    private final EmailService emailService;
    private final UserRepository userRepository;

 private final RedisTemplate<String,Object>redisTemplate;

    public void sendOtp(String email) {

        String key = "otp:" + email;
        String limitKey = "otp:limit:" + email;


        if (Boolean.TRUE.equals(redisTemplate.hasKey(limitKey))) {
            throw new RuntimeException("Çox tez-tez OTP istəyirsən");
        }

        String code = String.valueOf(new Random().nextInt(900000) + 100000);

        Otp otp = new Otp();
        otp.setEmail(email);
        otp.setCode(code);
        otp.setVerified(false);


        redisTemplate.opsForValue().set(key, otp, 5, TimeUnit.MINUTES);


        redisTemplate.opsForValue().set(limitKey, "1", 1, TimeUnit.MINUTES);

        emailService.sendEmail(email, "OTP Code", "Kod: " + code);
    }
    public void verifyOtp(String email, String code) {

        String key = "otp:" + email;

        Otp otp = (Otp) redisTemplate.opsForValue().get(key);

        if (otp == null) {
            throw new RuntimeException("OTP tapılmadı və ya vaxtı bitib");
        }

        if (!otp.getCode().equals(code)) {
            throw new RuntimeException("Kod səhvdir");
        }


        redisTemplate.delete(key);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("user not found"));

        user.setVerified(true);
        userRepository.save(user);
    }
}