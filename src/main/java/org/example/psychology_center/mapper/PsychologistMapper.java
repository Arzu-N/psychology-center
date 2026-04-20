package org.example.psychology_center.mapper;

import org.example.psychology_center.dao.entity.Psychologist;
import org.example.psychology_center.dto.request.PsychologistRequestDto;
import org.example.psychology_center.dto.response.PsychologistResponseDto;

public interface PsychologistMapper {
    Psychologist toPsychologistEntity(PsychologistRequestDto dto);
    PsychologistResponseDto toPsychologistResponseDto(Psychologist entity);
}
