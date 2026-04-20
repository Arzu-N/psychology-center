package org.example.psychology_center.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.psychology_center.dto.request.PsychologistRequestDto;
import org.example.psychology_center.dto.response.PsychologistResponseDto;
import org.example.psychology_center.service.PsychologistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/psychologist/api/v1")
public class PsychologistController {
    private final PsychologistService service;
@PostMapping
    public ResponseEntity<PsychologistResponseDto>createPsychologist(@Valid PsychologistRequestDto dto){
    return ResponseEntity.status(HttpStatus.CREATED).body(service.createPsychologist(dto));
}
@GetMapping
    public ResponseEntity<List<PsychologistResponseDto>>getPsychologist(){
    return ResponseEntity.ok(service.getAllPsychologist());
}

@GetMapping("/{id}")
public ResponseEntity<PsychologistResponseDto>getPsychologistById(@PathVariable Long id){
    return ResponseEntity.ok(service.getPsychologistById(id));
}

@GetMapping("/specialization")
    public ResponseEntity<List<PsychologistResponseDto>>getBySpecialization
        (@RequestParam String specialization){
    return ResponseEntity.ok(service.getBySpecialization(specialization));
}

@GetMapping("/experience")
    public ResponseEntity<List<PsychologistResponseDto>>getByExperience(@RequestParam Integer experience){
    return ResponseEntity.ok(service.getByExperience(experience));
}
}
