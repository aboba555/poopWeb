package com.poopProject.poopweb.repository;

import com.poopProject.poopweb.entity.ResetPassword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ResetPasswordRepository extends JpaRepository<ResetPassword,Long> {
    Optional<ResetPassword> findByToken(String token);
}
