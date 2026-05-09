package org.example.psychology_center.dao.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String message;

    boolean isRead;

    LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}