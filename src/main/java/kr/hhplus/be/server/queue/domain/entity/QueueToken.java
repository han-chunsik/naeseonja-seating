package kr.hhplus.be.server.queue.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class QueueToken {
    private Long id;
    private Long userId;
    private String token;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime activatedAt;

    public enum Status {
        WAITING, AVAILABLE, EXPIRED
    }

    @Builder
    public QueueToken(Long id, Long userId, String token, Status status, LocalDateTime createdAt, LocalDateTime activatedAt) {
        this.id = id;
        this.userId = userId;
        this.token = token;
        this.status = status;
        this.createdAt = createdAt;
        this.activatedAt = activatedAt;
    }

    public void changeStatus(Status status) {
        this.status = status;
    }
}
