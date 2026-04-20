package org.example.psychology_center.dao.repository;

import org.example.psychology_center.dao.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository<Test,Long> {
}
