package com.miroslav.orarend.authentication.entities;

import com.miroslav.orarend.pojo.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "resetTokens")
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token_hash", nullable = false, unique = true)
    private String tokenHash;

    private Instant createdDate;

    private Instant expiryDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public boolean isExpired() {
        return expiryDate.isBefore(Instant.now());
    }

    @PrePersist
    public void prePersist() {
        this.createdDate = Instant.now();
        this.expiryDate = this.createdDate.plusSeconds(15*60);
    }
}
