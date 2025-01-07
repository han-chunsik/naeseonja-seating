package kr.hhplus.be.server.app.queue.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "queue_token")
public class QueueToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "token", nullable = false, length = 36)
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    public enum Status {
        WAITING, AVAILABLE, EXPIRED
    }

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "activated_at", nullable = false)
    private LocalDateTime activatedAt;

    public QueueToken(Long id, Long userId, String token, Status status, LocalDateTime createdAt, LocalDateTime activatedAt) {
        this.id = id;
        this.userId = userId;
        this.token = token;
        this.status = status;
        this.createdAt = createdAt;
        this.activatedAt = activatedAt;
    }

    public QueueToken() {
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getActivatedAt() {
        return activatedAt;
    }
}
