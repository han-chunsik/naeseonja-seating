package kr.hhplus.be.server.queue.infrastructure.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "queue_token")
public class QueueTokenEntity {
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

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "activated_at")
    private LocalDateTime activatedAt;

    public QueueTokenEntity(Long id, Long userId, String token, Status status, LocalDateTime createdAt, LocalDateTime activatedAt) {
        this.id = id;
        this.userId = userId;
        this.token = token;
        this.status = status;
        this.createdAt = createdAt;
        this.activatedAt = activatedAt;
    }
}
