package org.example.psychology_center.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PsychologistRequestDto {
        @NotBlank(message="fullName must not be blank")
        String fullName;
        @NotBlank(message = "specialization must not be blank")
        String specialization;
        @Min(value=0,message = "experience must not be less than 0")
        @Max(value=60,message = "experience must not be greater than 60")
        Integer experience;
        @NotBlank(message = "language must not be blank")
        String language;
        @Size(max=500,message = "bio must not be greater than 500")
        String bio;
}
