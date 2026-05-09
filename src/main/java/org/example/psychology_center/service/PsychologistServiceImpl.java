package org.example.psychology_center.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.realm.UserDatabaseRealm;
import org.example.psychology_center.config.CustomUserDetails;
import org.example.psychology_center.dao.entity.Psychologist;
import org.example.psychology_center.dao.entity.User;
import org.example.psychology_center.dao.repository.PsychologistRepository;
import org.example.psychology_center.dao.repository.UserRepository;
import org.example.psychology_center.dto.request.PsychologistRequestDto;
import org.example.psychology_center.dto.response.PsychologistResponseDto;
import org.example.psychology_center.exception.NotFoundException;
import org.example.psychology_center.exception.ValidationException;
import org.example.psychology_center.mapper.PsychologistMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class PsychologistServiceImpl implements PsychologistService {

    private final PsychologistRepository psychologistRepository;
    private final PsychologistMapper mapper;
    private final UserRepository userRepository;

    @Value("${file.path}")
    private String path;


    @Override
    public PsychologistResponseDto createPsychologist(
            PsychologistRequestDto dto
    ) {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        Psychologist entity = mapper.toPsychologistEntity(dto);

        entity.setUser(user);

        return mapper.toPsychologistResponseDto(
                psychologistRepository.save(entity)
        );
    }

    @Override
    public Page<PsychologistResponseDto> getAllPsychologist(Pageable pageable) {
        return psychologistRepository.findAll(pageable)
                .map(mapper::toPsychologistResponseDto);
    }

    @Override
    public PsychologistResponseDto getPsychologistById(Long id) {
        Psychologist p = psychologistRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Psychologist not found"));

        return mapper.toPsychologistResponseDto(p);
    }

    @Override
    public Page<PsychologistResponseDto> getBySpecialization(String specialization, Pageable pageable) {
        return psychologistRepository
                .findBySpecializationContainingIgnoreCase(specialization, pageable)
                .map(mapper::toPsychologistResponseDto);
    }

    @Override
    public Page<PsychologistResponseDto> getByExperience(Integer experience, Pageable pageable) {
        return psychologistRepository
                .findByExperienceGreaterThanEqual(experience, pageable)
                .map(mapper::toPsychologistResponseDto);
    }



    @Override
    public String saveFile(MultipartFile file, String folder, String typePrefix) throws IOException {

        if (file.isEmpty()) {
            throw new NotFoundException("File is empty");
        }

        String contentType = file.getContentType();

        if (contentType == null || !contentType.startsWith(typePrefix)) {
            throw new ValidationException("Invalid file type");
        }

        String fileName =
                System.currentTimeMillis() + "_" +
                        file.getOriginalFilename().replace(" ", "_");

        Path uploadPath = Paths.get(path, folder);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);

        Files.write(filePath, file.getBytes());

        return fileName;
    }





    @Transactional
    public void attachFile(Long psychologistId,
                           String fileName,
                           String typePrefix) {

        Psychologist psychologist = psychologistRepository.findById(psychologistId)
                .orElseThrow(() -> new NotFoundException("Psychologist not found"));

        if (typePrefix.startsWith("image")) {
            psychologist.setImagePath(fileName);
        }

        else if (typePrefix.equals("application/pdf")) {
            psychologist.setCvPath(fileName);
        }

        else {
            throw new ValidationException("Unsupported file type");
        }
    }
}