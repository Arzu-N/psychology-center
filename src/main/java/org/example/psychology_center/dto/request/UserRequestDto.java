package org.example.psychology_center.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.psychology_center.util.Role;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequestDto {
    @NotBlank(message = "user must not be blank ")
    @Size(min = 3, max = 30, message = "userName must be greater than 3 and less than 30")
    String userName;
    @NotBlank(message = "surname must not be blank ")
    String surname;
    @NotBlank(message = "password not be blank")
    @Size(min = 6, max = 100, message = "password must be greater than 6")
    String password;
    @Email
    String email;



}
