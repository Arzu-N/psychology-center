package org.example.psychology_center.service;

import lombok.RequiredArgsConstructor;
import org.example.psychology_center.dao.entity.Psychologist;
import org.example.psychology_center.dao.repository.PsychologistRepository;
import org.example.psychology_center.dto.request.PsychologistRequestDto;
import org.example.psychology_center.dto.response.PsychologistResponseDto;
import org.example.psychology_center.exception.NotFoundException;
import org.example.psychology_center.mapper.PsychologistMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PsychologistServiceImpl implements PsychologistService{
private final PsychologistMapper mapper;
    private final PsychologistRepository psychologistRepository;
    @Value("${file.path}")
private  String path;
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
                new NotFoundException("Not found Psychologist")));
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

    public String upload( MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new NotFoundException("file not found");
        }

        String originalFilename = file.getOriginalFilename();

        Path uploadPath = Paths.get(path);


        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(originalFilename);

        Files.write(filePath, file.getBytes());

        return "file uploaded successfully " + originalFilename;
    }
    }

