package org.example.psychology_center.dao.repository;

import org.example.psychology_center.dao.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByPsychologist_IdAndAppointmentTime(Long psychologistId, LocalDateTime appointmentTime);
    List<Appointment> findByUserId(Long userId);
}
