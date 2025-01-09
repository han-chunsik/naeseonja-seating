package kr.hhplus.be.server.queue.domain.service;

import lombok.Getter;

@Getter
public enum QueueTokenLimit {
    ACTIVE_QUEUE_LIMIT(10);

    private final int limit;

    QueueTokenLimit(int limit) {
        this.limit = limit;
    }
}
