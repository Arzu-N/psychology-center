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

        public String uploadFile(MultipartFile file) throws IOException {
            if (file.isEmpty()) {
                throw new NotFoundException("file not found");
            }

            String contentType = file.getContentType();

            if (!"application/pdf".equals(contentType)) {
                throw new RuntimeException("Only PDF files are allowed");
            }

            String originalFilename = file.getOriginalFilename();

            Path uploadPath = Paths.get(path, "cv");

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(originalFilename);

            Files.write(filePath, file.getBytes());

            return "CV uploaded successfully: " + originalFilename;
        }
    public String uploadImage(MultipartFile image) throws IOException {
        if (image.isEmpty()) {
            throw new NotFoundException("image not found not found");
        }

        String contentType = image.getContentType();

        if (contentType == null || !contentType.startsWith("image/")) {
            throw new RuntimeException("fayl formati yalnisdir");
        }

        String originalFilename = image.getOriginalFilename();

        Path uploadPath = Paths.get(path, "images");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(originalFilename);

        Files.write(filePath, image.getBytes());

        return "Image uploaded successfully: " + originalFilename;
    }
    }

