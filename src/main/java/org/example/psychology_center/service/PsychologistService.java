package org.example.psychology_center.service;

import org.example.psychology_center.dto.request.PsychologistRequestDto;
import org.example.psychology_center.dto.response.PsychologistResponseDto;

import java.util.List;

public interface PsychologistService {

     PsychologistResponseDto createPsychologist(PsychologistRequestDto dto);
    List<PsychologistResponseDto>getAllPsychologist();
    PsychologistResponseDto getPsychologistById(Long id);
    List<PsychologistResponseDto>getBySpecialization(String specialization);
    List<PsychologistResponseDto>getByExperience(Integer experience);

}
