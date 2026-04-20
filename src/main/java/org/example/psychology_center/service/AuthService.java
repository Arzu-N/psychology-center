package org.example.psychology_center.service;

import org.example.psychology_center.dto.request.UserRequestDto;


public interface AuthService {
   void register(UserRequestDto dto);
   String login (UserRequestDto dto);
}
