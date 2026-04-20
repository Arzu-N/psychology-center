package org.example.psychology_center.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.psychology_center.dto.request.AppointmentRequestDto;
import org.example.psychology_center.dto.response.AppointmentResponseDto;
import org.example.psychology_center.service.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appointment/api/v1")
public class AppointmentController {
    private final AppointmentService service;
    @PostMapping("/create")
    public ResponseEntity<AppointmentResponseDto>createAppointment(@RequestBody @Valid AppointmentRequestDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createAppointment(dto));
    }
    @GetMapping("/{userId}")
    public ResponseEntity<List<AppointmentResponseDto>> getUserAppointments(@PathVariable Long userId){
        return ResponseEntity.ok(service.getUserAppointments(userId));
    }
}
