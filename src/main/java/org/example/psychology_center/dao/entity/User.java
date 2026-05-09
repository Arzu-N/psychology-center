package org.example.psychology_center.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.psychology_center.util.Role;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "user_table")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true)
    String userName;
    String surname;

    String fullName;

    String password;

    @Column(unique = true)
    String email;

    @Enumerated(EnumType.STRING)
    Role role;

    boolean verified;
}