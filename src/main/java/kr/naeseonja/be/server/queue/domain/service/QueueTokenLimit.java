package kr.naeseonja.be.server.queue.domain.service;

import lombok.Getter;

@Getter
public enum QueueTokenLimit {
    ACTIVE_QUEUE_LIMIT(78),
    WAITING_TOKEN_EXPIRATION_TIME_HOUR(300),
    ACTIVE_TOKEN_EXPIRATION_TIME_MINUTE(10);

    private final int limit;

    QueueTokenLimit(int limit) {
        this.limit = limit;
    }
}
