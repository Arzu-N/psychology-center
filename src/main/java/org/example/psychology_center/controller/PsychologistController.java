package org.example.psychology_center.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.example.psychology_center.dto.request.PsychologistRequestDto;
import org.example.psychology_center.dto.response.PsychologistResponseDto;
import org.example.psychology_center.service.AppointmentService;
import org.example.psychology_center.service.PsychologistService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/psychologist/api/v1")
public class PsychologistController {

    private final PsychologistService service;

    // ADMIN
    @PostMapping("/manage")
    public ResponseEntity<PsychologistResponseDto> create(@RequestBody @Valid PsychologistRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createPsychologist(dto));
    }

    // PUBLIC
    @GetMapping("/public")
    public Page<PsychologistResponseDto> getAll(Pageable pageable) {
        return service.getAllPsychologist(pageable);
    }

    @GetMapping("/public/{id:\\d+}")
    public ResponseEntity<PsychologistResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getPsychologistById(id));
    }

    // PSYCHOLOGIST OR ADMIN
    @PostMapping("/manage/{id}/cv")
    public ResponseEntity<String> uploadCv(@PathVariable Long id,
                                           @RequestPart MultipartFile file) throws IOException {

        String fileName = service.saveFile(file, "cv", "application/pdf");
        service.attachFile(id, fileName, "application/pdf");

        return ResponseEntity.ok("CV uploaded");
    }

    @PostMapping("/manage/{id}/image")
    public ResponseEntity<String> uploadImage(@PathVariable Long id,
                                              @RequestPart MultipartFile image) throws IOException {

        String fileName = service.saveFile(image, "images", "image/");
        service.attachFile(id, fileName, "image/");

        return ResponseEntity.ok("Image uploaded");
    }

    @GetMapping("/public/specialization")
    public Page<PsychologistResponseDto> getBySpecialization(
            @RequestParam String specialization,
            Pageable pageable
    ) {
        return service.getBySpecialization(specialization, pageable);
    }

    @GetMapping("/public/experience")
    public Page<PsychologistResponseDto> getByExperience(
            @RequestParam Integer experience,
            Pageable pageable
    ) {
        return service.getByExperience(experience, pageable);
    }
}