package org.example.psychology_center.service;

import lombok.RequiredArgsConstructor;
import org.example.psychology_center.config.AppConfig;
import org.example.psychology_center.dao.entity.Psychologist;
import org.example.psychology_center.dao.entity.User;
import org.example.psychology_center.dao.repository.PsychologistRepository;
import org.example.psychology_center.dao.repository.UserRepository;
import org.example.psychology_center.dto.request.UserRequestDto;
import org.example.psychology_center.dto.response.AuthResponse;
import org.example.psychology_center.exception.UserAlreadyExists;
import org.example.psychology_center.exception.UserNotFound;
import org.example.psychology_center.util.JwtUtil;
import org.example.psychology_center.util.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final AppConfig config;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    public void register(UserRequestDto userDTO) {

        if (userRepository.existsByUserName(userDTO.getUserName())) {
            throw new UserAlreadyExists("User already exists");
        }

        User user = new User();
        user.setUserName(userDTO.getUserName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);
    }

    public void registerAdmin(UserRequestDto userDTO) {
        User user = new User();
        user.setUserName(userDTO.getUserName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(Role.ADMIN);

        userRepository.save(user);
    }

    public AuthResponse login(UserRequestDto request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserName(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUserName(request.getUserName())
                .orElseThrow(() -> new UserNotFound("User not found"));

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
                .orElseThrow(() -> new UserNotFound("User not found"));

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
}


