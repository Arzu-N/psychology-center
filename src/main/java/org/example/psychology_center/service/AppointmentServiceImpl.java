package org.example.psychology_center.service;

import lombok.RequiredArgsConstructor;
import org.example.psychology_center.config.CustomUserDetails;
import org.example.psychology_center.dao.entity.Appointment;
import org.example.psychology_center.dao.entity.Psychologist;
import org.example.psychology_center.dao.entity.User;
import org.example.psychology_center.dao.repository.AppointmentRepository;
import org.example.psychology_center.dao.repository.PsychologistRepository;
import org.example.psychology_center.dao.repository.UserRepository;
import org.example.psychology_center.dto.request.AppointmentRequestDto;
import org.example.psychology_center.dto.response.AppointmentResponseDto;
import org.example.psychology_center.exception.AlreadyExistsException;
import org.example.psychology_center.exception.NotFoundException;
import org.example.psychology_center.exception.ValidationException;
import org.example.psychology_center.mapper.AppointmentMapper;
import org.example.psychology_center.util.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final NotificationService notificationService;
    private final PsychologistRepository psychologistRepository;
    private final AppointmentMapper appointmentMapper;

    private User getCurrentUser() {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return customUserDetails.getUser();
    }

    @Override
    public AppointmentResponseDto createAppointment(AppointmentRequestDto dto) {

        User user = getCurrentUser();

        Psychologist psychologist = psychologistRepository.findById(dto.getPsychologistId())
                .orElseThrow(() -> new NotFoundException("Psychologist not found"));

        if (dto.getAppointmentTime().isBefore(LocalDateTime.now())) {
            throw new ValidationException("Appointment time cannot be in the past");
        }

        boolean exists = appointmentRepository
                .existsByPsychologistIdAndAppointmentTime(
                        psychologist.getId(),
                        dto.getAppointmentTime()
                );

        if (exists) {
            throw new AlreadyExistsException("This time is already reserved");
        }

        Appointment appointment = Appointment.builder()
                .user(user)
                .psychologist(psychologist)
                .appointmentTime(dto.getAppointmentTime())
                .confirmed(false)
                .build();

        Appointment saved = appointmentRepository.save(appointment);

        notificationService.sendNotification(
                user,
                "Appointment booked at " + dto.getAppointmentTime()
        );

        return appointmentMapper.toAppointmentResponseDto(saved);
    }

    @Override
    public Page<AppointmentResponseDto> getAppointment(int page, int size) {

        User user = getCurrentUser();

        Pageable pageable = PageRequest.of(
                Math.max(page, 0),
                Math.min(size, 50),
                Sort.by("appointmentTime").descending()
        );

        if (user.getRole() == Role.ROLE_USER) {
            return appointmentRepository.findByUserId(user.getId(), pageable)
                    .map(appointmentMapper::toAppointmentResponseDto);
        }

        if (user.getRole() == Role.ROLE_PSYCHOLOGIST) {
            return appointmentRepository.findByPsychologist_User_Id(user.getId(), pageable)
                    .map(appointmentMapper::toAppointmentResponseDto);
        }

        if (user.getRole() == Role.ROLE_ADMIN) {
            return appointmentRepository.findAll(pageable)
                    .map(appointmentMapper::toAppointmentResponseDto);
        }

        throw new RuntimeException("Invalid role");
    }
    public void confirmAppointment(Long appointmentId) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new NotFoundException("Appointment not found"));

        appointment.setConfirmed(true);

        appointmentRepository.save(appointment);
    }
}