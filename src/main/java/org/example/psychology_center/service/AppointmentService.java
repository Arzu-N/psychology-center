package org.example.psychology_center.service;

import org.example.psychology_center.dto.request.AppointmentRequestDto;
import org.example.psychology_center.dto.response.AppointmentResponseDto;
import org.springframework.data.domain.Page;


public interface AppointmentService {
    AppointmentResponseDto createAppointment(AppointmentRequestDto dto);

    Page<AppointmentResponseDto> getAppointment(int page, int size);

    void confirmAppointment(Long appointmentId);
}
