package org.example.psychology_center.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.psychology_center.util.Role;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponseDto {
    Long id;
    String userName;
    String password;
    Role role;
}
