package org.example.psychology_center.dao.repository;

import org.example.psychology_center.dao.entity.User;
import org.example.psychology_center.util.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUserName(String userName);

    boolean existsByUserName(String userName);

    List<User> findByRole(Role role);

}
