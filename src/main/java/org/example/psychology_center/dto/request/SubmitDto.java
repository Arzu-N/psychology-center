package org.example.psychology_center.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubmitDto {
    @NotNull
    Long userId;
    @NotNull
    Long testId;
    @NotNull
    List<Long> answerIds;
}
