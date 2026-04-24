package org.example.psychology_center.mapper;

import lombok.RequiredArgsConstructor;
import org.example.psychology_center.dao.entity.User;
import org.example.psychology_center.dao.repository.UserRepository;
import org.example.psychology_center.dto.request.UserRequestDto;
import org.example.psychology_center.dto.response.UserResponseDto;
import org.example.psychology_center.util.Role;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper{
    private final UserRepository userRepository;

    @Override
    public User toUserEntity(UserRequestDto dto) {
       return User.builder()
                .userName(dto.getUserName())
               .fullName(dto.getUserName()+" "+dto.getSurname())
                .password(dto.getPassword())
                .role(Role.ROLE_USER)
                .build();
    }

    @Override
    public UserResponseDto toUserResponsedto(User entity) {
        return UserResponseDto.builder()
                .id(entity.getId())
                .userName(entity.getUserName())
                .password(entity.getPassword())
                .role(entity.getRole())
                .build();
    }
}
