package org.example.psychology_center.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class AppointmentRequestDto {

    @NotNull
    Long psychologistId;
    @NotNull
    LocalDateTime appointmentTime;
}
