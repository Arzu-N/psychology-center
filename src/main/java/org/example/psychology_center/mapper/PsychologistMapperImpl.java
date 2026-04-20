package org.example.psychology_center.mapper;

import lombok.RequiredArgsConstructor;
import org.example.psychology_center.dao.entity.Psychologist;
import org.example.psychology_center.dto.request.PsychologistRequestDto;
import org.example.psychology_center.dto.response.PsychologistResponseDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PsychologistMapperImpl implements PsychologistMapper {

    public Psychologist toPsychologistEntity(PsychologistRequestDto dto){
      return  Psychologist.builder()
               .fullName(dto.getFullName())
               .specialization(dto.getSpecialization())
               .experience(dto.getExperience())
               .language(dto.getLanguage())
               .bio(dto.getBio())
               .build();
    }

    public PsychologistResponseDto toPsychologistResponseDto(Psychologist entity){
      return  PsychologistResponseDto.builder()
                .fullName(entity.getFullName())
                .specialization(entity.getSpecialization())
                .experience(entity.getExperience())
                .language(entity.getLanguage())
                .bio(entity.getBio())
                .build();
    }
}
