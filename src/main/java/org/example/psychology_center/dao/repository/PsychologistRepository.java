package org.example.psychology_center.dao.repository;

import org.example.psychology_center.dao.entity.Psychologist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PsychologistRepository extends JpaRepository<Psychologist,Long> {
    List<Psychologist> findBySpecialization(String specification);
    List<Psychologist>findPsychologistByExperienceGreaterThanEqual(Integer experience);

}
