package org.example.psychology_center.mapper;

import lombok.RequiredArgsConstructor;
import org.example.psychology_center.dao.entity.Appointment;
import org.example.psychology_center.dao.repository.PsychologistRepository;
import org.example.psychology_center.dao.repository.UserRepository;
import org.example.psychology_center.dto.request.AppointmentRequestDto;
import org.example.psychology_center.dto.response.AppointmentResponseDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppointmentMapperImpl implements AppointmentMapper {
    private final UserRepository userRepository;
    private final PsychologistRepository psychologistRepository;

    @Override
    public Appointment toAppointmentDto(AppointmentRequestDto dto) {
       return Appointment.builder()
                .user(userRepository.findById(dto.getUserId())
                        .orElseThrow(()->new RuntimeException("User not found")))
                .psychologist(psychologistRepository.findById(dto.getPsychologistId())
                        .orElseThrow(()->new RuntimeException("Psychologist not found")))
                .appointmentTime(dto.getAppointmentTime())
                .confirmed(true)
                .build();
    }

    @Override
    public AppointmentResponseDto toAppointmentResponseDto(Appointment entity) {
       return AppointmentResponseDto.builder()
                .id(entity.getId())
                .userId(entity.getUser().getId())
                .psychologistId(entity.getPsychologist().getId())
                .appointmentTime(entity.getAppointmentTime())
                .confirmed(entity.isConfirmed())
                .build();
    }
}
