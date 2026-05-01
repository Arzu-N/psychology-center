package org.example.psychology_center.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
    @NotBlank
    private String userName;
@Size(min=6,message = "password length not less than 6")
    private String password;
}