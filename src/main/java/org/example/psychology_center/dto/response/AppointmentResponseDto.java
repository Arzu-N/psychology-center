package org.example.psychology_center.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class AppointmentResponseDto {
    Long id;
    Long userId;
    Long psychologistId;
    LocalDateTime appointmentTime;
    boolean confirmed;
}
