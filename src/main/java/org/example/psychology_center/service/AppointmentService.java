package org.example.psychology_center.service;

import lombok.RequiredArgsConstructor;
import org.example.psychology_center.dao.entity.Appointment;
import org.example.psychology_center.dto.request.AppointmentRequestDto;
import org.example.psychology_center.dto.response.AppointmentResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AppointmentService {
     AppointmentResponseDto createAppointment(AppointmentRequestDto dto);
     List<AppointmentResponseDto> getUserAppointments(Long userID);
}
