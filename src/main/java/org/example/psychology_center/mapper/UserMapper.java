package org.example.psychology_center.mapper;

import org.example.psychology_center.dao.entity.User;
import org.example.psychology_center.dto.request.UserRequestDto;
import org.example.psychology_center.dto.response.UserResponseDto;

public interface UserMapper {
    User toUserEntity(UserRequestDto dto);
    UserResponseDto toUserResponsedto(User entity);
}
