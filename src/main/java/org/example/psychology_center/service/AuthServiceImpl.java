package org.example.psychology_center.service;

import lombok.RequiredArgsConstructor;
import org.example.psychology_center.dao.entity.User;
import org.example.psychology_center.dao.repository.UserRepository;
import org.example.psychology_center.dto.request.UserRequestDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Override
    public void register(UserRequestDto dto) {
        userRepository.findUserByUserName(dto.getUserName())
                .ifPresent(user->{
                    throw new RuntimeException("User already exists");});
    }

    @Override
    public String login(UserRequestDto dto) {
        User user = userRepository.findUserByUserName(dto.getUserName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(!(dto.getUserName().equals(user.getUserName())))
            throw new RuntimeException("Password incorrect");
        return "Success login";
    }
}
