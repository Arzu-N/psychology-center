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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appointment/api/v1")
public class AppointmentController {
    private final AppointmentService service;



    @GetMapping
    public Page<AppointmentResponseDto> getUserAppointments(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return service.getUserAppointments(page, size);
    }


    @PostMapping
    public AppointmentResponseDto createAppointment(@RequestBody AppointmentRequestDto dto) {
        return service.createAppointment(dto);
    }
}
