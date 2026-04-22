package org.example.psychology_center.service;

import org.example.psychology_center.dto.request.UserRequestDto;
import org.example.psychology_center.dto.response.AuthResponse;


public interface AuthService {
   void register(UserRequestDto dto);
   AuthResponse login (UserRequestDto dto);
    AuthResponse refreshToken(String refreshToken);
    public void registerAdmin(UserRequestDto userDTO);
}
