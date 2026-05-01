package org.example.psychology_center.service;

import org.example.psychology_center.dto.request.LoginRequestDto;
import org.example.psychology_center.dto.request.RegisterRequest;
import org.example.psychology_center.dto.request.UserRequestDto;
import org.example.psychology_center.dto.response.AuthResponse;
import org.example.psychology_center.util.Role;


public interface AuthService {
     void register(RegisterRequest request);
   AuthResponse login (LoginRequestDto dto);
    AuthResponse refreshToken(String refreshToken);
     void changeRole(Long userId, Role role);
}
