package kr.hhplus.be.server.queue.domain.repository;

import kr.hhplus.be.server.queue.domain.entity.QueueToken;

public interface QueueTokenRepository {
    QueueToken findFirstByUserId(Long userId);
}
