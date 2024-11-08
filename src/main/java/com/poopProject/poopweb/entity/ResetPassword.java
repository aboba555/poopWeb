package com.poopProject.poopweb.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@Table(name = "reset_password")
@NoArgsConstructor
@AllArgsConstructor
public class ResetPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String token;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime createdAt = LocalDateTime.now();

    private boolean active = true;

    public ResetPassword(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ResetPassword(String token, User user) {
        this.token = token;
        this.user = user;
    }
}
