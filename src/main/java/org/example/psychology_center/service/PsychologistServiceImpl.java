package org.example.psychology_center.service;

import lombok.RequiredArgsConstructor;
import org.example.psychology_center.dao.entity.Psychologist;
import org.example.psychology_center.dao.repository.PsychologistRepository;
import org.example.psychology_center.dto.request.PsychologistRequestDto;
import org.example.psychology_center.dto.response.PsychologistResponseDto;
import org.example.psychology_center.exception.NotFoundPsychologist;
import org.example.psychology_center.mapper.PsychologistMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PsychologistServiceImpl implements PsychologistService{
private final PsychologistMapper mapper;
    private final PsychologistRepository psychologistRepository;

    @Override
    public PsychologistResponseDto createPsychologist(PsychologistRequestDto dto) {
        Psychologist psychologistEntity = mapper.toPsychologistEntity(dto);
      return mapper.toPsychologistResponseDto(psychologistRepository.save(psychologistEntity));
    }

    @Override
    public List<PsychologistResponseDto> getAllPsychologist() {
       return psychologistRepository.findAll().stream().map(mapper::toPsychologistResponseDto).toList();
    }

    @Override
    public PsychologistResponseDto getPsychologistById(Long id) {
       return mapper.toPsychologistResponseDto(psychologistRepository.findById(id).orElseThrow(()->
                new NotFoundPsychologist("Not found Psychologist")));
    }

    @Override
    public List<PsychologistResponseDto> getBySpecialization(String specialization) {
        return psychologistRepository.findBySpecialization(specialization)
                .stream().map(mapper::toPsychologistResponseDto).toList();
    }

    @Override
    public List<PsychologistResponseDto>getByExperience(Integer experience){
        return psychologistRepository.findPsychologistByExperienceGreaterThanEqual(experience)
                .stream().map(mapper::toPsychologistResponseDto).toList();
    }
}
