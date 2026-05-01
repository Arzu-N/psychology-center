package org.example.psychology_center.dto.request;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String userName;
    @Column(unique = true)
    private String email;
    private String password;
}