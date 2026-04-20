package org.example.psychology_center.service;

import lombok.RequiredArgsConstructor;
import org.example.psychology_center.dao.entity.Appointment;
import org.example.psychology_center.dao.entity.Psychologist;
import org.example.psychology_center.dao.entity.User;
import org.example.psychology_center.dao.repository.AppointmentRepository;
import org.example.psychology_center.dao.repository.PsychologistRepository;
import org.example.psychology_center.dao.repository.UserRepository;
import org.example.psychology_center.dto.request.AppointmentRequestDto;
import org.example.psychology_center.dto.response.AppointmentResponseDto;
import org.example.psychology_center.mapper.AppointmentMapper;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService{
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final PsychologistRepository psychologistRepository;
    private final AppointmentMapper appointmentMapper;

    @Override
    public AppointmentResponseDto createAppointment(AppointmentRequestDto dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow(() ->
                new RuntimeException("User not found"));
        Psychologist psychologist = psychologistRepository.findById(dto.getPsychologistId())
                .orElseThrow(() -> new RuntimeException("Psychologist not found"));
        if(!(appointmentRepository
                .existsByPsychologist_IdAndAppointmentTime(
                        psychologist.getId(), dto.getAppointmentTime()
                )))
            throw new RuntimeException("This time is already full");
        Appointment appointment = Appointment.builder()
                .user(user)
                .psychologist(psychologist)
                .appointmentTime(dto.getAppointmentTime())
                .build();
        return appointmentMapper.toAppointmentResponseDto(appointment);
    }

    @Override
    public List<AppointmentResponseDto> getUserAppointments(Long userID) {
        return appointmentRepository.findByUserId(userID).stream()
                .map(appointmentMapper::toAppointmentResponseDto).toList();
    }
}
