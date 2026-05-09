package org.example.psychology_center.service;

import org.example.psychology_center.dto.request.PsychologistRequestDto;
import org.example.psychology_center.dto.response.PsychologistResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PsychologistService {

    PsychologistResponseDto createPsychologist(PsychologistRequestDto dto);

    Page<PsychologistResponseDto> getAllPsychologist(Pageable pageable);

    PsychologistResponseDto getPsychologistById(Long id);

    Page<PsychologistResponseDto> getByExperience(Integer experience, Pageable pageable);

    Page<PsychologistResponseDto> getBySpecialization(String specialization, Pageable pageable);

    String saveFile(MultipartFile file, String folder, String typePrefix) throws IOException;

    void attachFile(Long psychologistId, String fileName, String typePrefix);
}
