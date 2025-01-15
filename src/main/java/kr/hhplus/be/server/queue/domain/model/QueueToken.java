package kr.hhplus.be.server.queue.domain.model;

import kr.hhplus.be.server.queue.exception.QueueErrorCode;
import kr.hhplus.be.server.queue.exception.QueueException;
import kr.hhplus.be.server.queue.infrastructure.jpa.QueueTokenEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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

    public QueueToken(Long id, Long userId, String token, Status status, LocalDateTime createdAt, LocalDateTime activatedAt) {
        this.id = id;
        this.userId = userId;
        this.token = token;
        this.status = status;
        this.createdAt = createdAt;
        this.activatedAt = activatedAt;
    }

    public void setQueueTokenExpired() {
        this.status = Status.EXPIRED;
    }

    public void setQueueTokenActivated() {
        this.status = Status.AVAILABLE;
        this.activatedAt = LocalDateTime.now();
    }

    public static long getPositionByToken(List<QueueToken> queueTokens, String token) {
        return (long) queueTokens.stream()
                .map(QueueToken::getToken)
                .toList()
                .indexOf(token) + 1;
    }

    public boolean isAvailable() {
        return status == Status.AVAILABLE;
    }

    public boolean isWaiting() {
        return status == Status.WAITING;
    }

    public void validateExpiredToken() {
        if (status == Status.EXPIRED) {
            throw new QueueException(QueueErrorCode.EXPIRED_TOKEN, token);
        }
    }

    public static QueueToken createToken(Long userId) {
        String token = UUID.randomUUID().toString();  // UUID 생성
        LocalDateTime now = LocalDateTime.now();

        return new QueueToken(
            null,
            userId,
            token,
            Status.WAITING,
            now,
            null
        );
    }

    public static QueueToken fromEntity(QueueTokenEntity entity) {
        return new QueueToken(
                entity.getId(),
                entity.getUserId(),
                entity.getToken(),
                Status.valueOf(entity.getStatus().name()),
                entity.getCreatedAt(),
                entity.getActivatedAt()
        );
    }

    public QueueTokenEntity toEntity() {
        return new QueueTokenEntity(
                this.id,
                this.userId,
                this.token,
                QueueTokenEntity.Status.valueOf(this.status.name()),
                this.createdAt,
                this.activatedAt
        );
    }
}
