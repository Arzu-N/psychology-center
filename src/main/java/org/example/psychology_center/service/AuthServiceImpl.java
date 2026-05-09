package org.example.psychology_center.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.psychology_center.dao.entity.User;
import org.example.psychology_center.dao.repository.UserRepository;
import org.example.psychology_center.dto.request.LoginRequestDto;
import org.example.psychology_center.dto.request.RegisterRequest;
import org.example.psychology_center.dto.response.AuthResponse;
import org.example.psychology_center.exception.AlreadyExistsException;
import org.example.psychology_center.exception.NotFoundException;
import org.example.psychology_center.exception.ValidationException;
import org.example.psychology_center.util.JwtUtil;
import org.example.psychology_center.util.Role;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final NotificationService notificationService;
    private final OtpService otpService;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    @Transactional
    public void register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistsException("Email already exists");
        }

        if (userRepository.existsByUserName(request.getUserName())) {
            throw new AlreadyExistsException("Username already exists");
        }

        User user = new User();
        user.setUserName(request.getUserName());
        user.setSurname(request.getSurname());
        user.setRole(Role.ROLE_USER);
        user.setFullName(String.format("%s %s",
                request.getUserName(),
                request.getSurname()
        ));

        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setVerified(false);

        userRepository.save(user);

        notificationService.sendNotification(
                user,
                "Welcome " + user.getUserName()
        );

        otpService.sendOtp(user.getEmail());
    }

    @Override
    public AuthResponse login(LoginRequestDto request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserName(),
                        request.getPassword()
                )
        );


        User user = userRepository.findByUserName(request.getUserName())
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!user.isVerified()) {
            throw new ValidationException("Email təsdiqlənməyib");
        }

        String accessToken = jwtUtil.generateToken(
                user.getUserName(),
                user.getRole()
        );

        String refreshToken = refreshTokenService.createRefreshToken(user.getUserName());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .build();
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {

        String username =
                refreshTokenService.getUsernameByToken(refreshToken);

        refreshTokenService.validateRefreshToken(
                username,
                refreshToken
        );

        String newRefreshToken =
                refreshTokenService.rotateRefreshToken(username);

        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new NotFoundException("User not found"));

        String newAccessToken = jwtUtil.generateToken(
                user.getUserName(),
                user.getRole()
        );

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .tokenType("Bearer")
                .build();
    }

    @Override
    public void logout(String refreshToken) {

        String username =
                refreshTokenService.getUsernameByToken(refreshToken);

        refreshTokenService.logout(username, refreshToken);
    }

    @Transactional
    @Override
    public void changeRole(Long userId, Role role) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        user.setRole(role);
    }
}

