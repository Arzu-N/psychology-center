package org.example.psychology_center.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.psychology_center.dto.request.AppointmentRequestDto;
import org.example.psychology_center.dto.response.AppointmentResponseDto;
import org.example.psychology_center.service.AppointmentService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appointment/api/v1")
public class AppointmentController {

    private final AppointmentService service;

    // USER only
    @PostMapping("/create")
    public ResponseEntity<AppointmentResponseDto> createAppointment(
            @RequestBody @Valid AppointmentRequestDto dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createAppointment(dto));
    }

    // USER + PSYCHOLOGIST
    @GetMapping("/my")
    public ResponseEntity<Page<AppointmentResponseDto>> getAppointment(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(service.getAppointment(page, size));
    }

    // PSYCHOLOGIST confirm
    @PutMapping("/confirm/{id}")
    public void confirm(@PathVariable Long id) {
        service.confirmAppointment(id);
    }
}