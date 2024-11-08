package com.poopProject.poopweb.service;

import com.poopProject.poopweb.entity.ResetPassword;
import com.poopProject.poopweb.entity.User;
import com.poopProject.poopweb.repository.ResetPasswordRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ResetPasswordService {

    ResetPasswordRepository repository;

    private static final Duration TOKEN_VALIDATION_DURATION = Duration.ofMinutes(5);


    public String createToken(User user){
        String token = UUID.randomUUID().toString();
        ResetPassword resetPassword = new ResetPassword(token,user);
        repository.save(resetPassword);
        return token;
    }

    public boolean IsExpired(ResetPassword resetPassword){
        return resetPassword.getCreatedAt().plus(TOKEN_VALIDATION_DURATION).isBefore(LocalDateTime.now());
    }

    public Optional<ResetPassword> findToken(String token) {
        return repository.findByToken(token);
    }

    public void deleteToken(ResetPassword token) {
        repository.delete(token);
    }


}
