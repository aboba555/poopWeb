package com.poopProject.poopweb.config;

import com.poopProject.poopweb.repository.ResetPasswordRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class TokenCleanup {

    private final ResetPasswordRepository repository;

    @Scheduled(fixedRate = 86400000)
    public void cleanupExpiredTokens() {
        LocalDateTime expirationThreshold = LocalDateTime.now().minusDays(1);
        repository.deleteByCreatedAtBeforeAndActiveIsFalse(expirationThreshold);
    }
}
