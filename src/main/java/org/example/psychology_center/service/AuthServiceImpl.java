package org.example.psychology_center.service;

import lombok.RequiredArgsConstructor;
import org.example.psychology_center.config.AppConfig;
import org.example.psychology_center.dao.entity.User;
import org.example.psychology_center.dao.repository.UserRepository;
import org.example.psychology_center.dto.request.UserRequestDto;
import org.example.psychology_center.dto.response.AuthResponse;
import org.example.psychology_center.exception.AlreadyExists;
import org.example.psychology_center.exception.NotFound;
import org.example.psychology_center.util.JwtUtil;
import org.example.psychology_center.util.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final AppConfig config;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final NotificationService notificationService;

    public void register(UserRequestDto userDTO) {

        if (userRepository.existsByUserName(userDTO.getUserName())) {
            throw new AlreadyExists("User already exists");
        }

        User user = new User();
        user.setUserName(userDTO.getUserName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(Role.ROLE_USER);

        userRepository.save(user);
        // user notification
        notificationService.sendNotification(user, "Qeydiyyat uğurla tamamlandı");
        sendNotification(user);
}


    public AuthResponse login(UserRequestDto request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserName(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUserName(request.getUserName())
                .orElseThrow(() -> new NotFound("User not found"));

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

    public AuthResponse refreshToken(String refreshToken) {

        String username = refreshTokenService.validateRefreshToken(refreshToken);

        if (username == null) {
            throw new RuntimeException("Invalid refresh token");
        }

        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new NotFound("User not found"));

        String newAccessToken = jwtUtil.generateToken(
                user.getUserName(),
                user.getRole()
        );

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .build();
    }
    public void sendNotification(User user){



// admin notification

        List<User> admins = userRepository.findByRole(Role.ROLE_ADMIN);

        for (User admin : admins) {
            notificationService.sendNotification(
                    admin,
                    "Yeni user qeydiyyatdan keçdi: " + user.getUserName()
            );
        }
    }

    public void changeRole(Long userId, Role role) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFound("User not found"));

        user.setRole(role);

        userRepository.save(user);
    }
}


