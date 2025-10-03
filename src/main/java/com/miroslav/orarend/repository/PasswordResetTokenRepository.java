package com.miroslav.orarend.repository;

import com.miroslav.orarend.authentication.entities.PasswordResetToken;
import com.miroslav.orarend.pojo.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByTokenHash(String tokenHash);

    Optional<PasswordResetToken> findByUser(User user);

    void deleteByUser(User user);

    boolean existsByUser(User user);

    @Modifying
    @Transactional
    void deleteAllByExpiryDateBefore(Instant now);
}
