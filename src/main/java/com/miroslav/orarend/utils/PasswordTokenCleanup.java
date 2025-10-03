package com.miroslav.orarend.utils;

import com.miroslav.orarend.repository.PasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class PasswordTokenCleanup {

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Scheduled(cron = "0 0 2 * * *")
    public void cleanExpiredTokens() {
        passwordResetTokenRepository.deleteAllByExpiryDateBefore(Instant.now());
    }
}
