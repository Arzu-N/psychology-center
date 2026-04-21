package org.example.psychology_center.dao.repository;

import org.example.psychology_center.dao.entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByPsychologist_IdAndAppointmentTime(Long psychologistId, LocalDateTime appointmentTime);

    Page<Appointment> findByUserId(Long userId, Pageable pageable);
}
