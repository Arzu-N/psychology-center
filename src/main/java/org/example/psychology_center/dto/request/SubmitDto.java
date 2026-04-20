package org.example.psychology_center.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubmitDto {
    Long userId;
    Long testId;
    List<Long> answerIds;
}
