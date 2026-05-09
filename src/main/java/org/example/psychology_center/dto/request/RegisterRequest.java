package org.example.psychology_center.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    @NotBlank(message="userName bos ola bilmez")
    private String userName;
    @NotBlank(message="surname bos ola bilmez")
    private String surname;
    @NotBlank(message="email bos ola bilmez")
    @Email(message = "Email duzgun formatda deyil")
    private String email;
    @NotBlank(message="password bos ola bilmez")
    @Size(min = 6, message = "password minimum 6 simvol olmalıdır")
    private String password;
}