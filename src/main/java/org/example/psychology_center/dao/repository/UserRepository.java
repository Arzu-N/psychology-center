package org.example.psychology_center.dao.repository;

import org.example.psychology_center.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User>findUserByUserName(String userName);
}
