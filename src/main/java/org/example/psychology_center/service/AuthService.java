package org.example.psychology_center.service;

import org.example.psychology_center.dto.request.UserRequestDto;
import org.example.psychology_center.dto.response.AuthResponse;
import org.example.psychology_center.util.Role;


public interface AuthService {
   void register(UserRequestDto dto);
   AuthResponse login (UserRequestDto dto);
    AuthResponse refreshToken(String refreshToken);
     void changeRole(Long userId, Role role);
}
