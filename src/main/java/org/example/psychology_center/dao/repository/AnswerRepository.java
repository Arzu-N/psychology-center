package org.example.psychology_center.dao.repository;

import org.example.psychology_center.dao.entity.Answer;
import org.example.psychology_center.dao.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer,Long> {
}
