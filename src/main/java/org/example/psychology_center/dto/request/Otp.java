package org.example.psychology_center.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Otp {
    private String email;
    private String code;
    private boolean verified;
}
