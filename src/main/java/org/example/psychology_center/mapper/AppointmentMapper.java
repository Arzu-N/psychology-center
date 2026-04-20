package org.example.psychology_center.mapper;

import org.example.psychology_center.dao.entity.Appointment;
import org.example.psychology_center.dto.request.AppointmentRequestDto;
import org.example.psychology_center.dto.response.AppointmentResponseDto;

public interface AppointmentMapper {
    Appointment toAppointmentDto(AppointmentRequestDto dto);
    AppointmentResponseDto toAppointmentResponseDto(Appointment entity);
}
