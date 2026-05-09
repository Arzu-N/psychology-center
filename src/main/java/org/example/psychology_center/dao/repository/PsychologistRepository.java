package org.example.psychology_center.dao.repository;

import org.example.psychology_center.dao.entity.Psychologist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PsychologistRepository extends JpaRepository<Psychologist,Long> {
    Page<Psychologist> findBySpecializationContainingIgnoreCase(String specialization, Pageable pageable);

    Page<Psychologist> findByExperienceGreaterThanEqual(Integer experience, Pageable pageable);}
