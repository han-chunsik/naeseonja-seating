package kr.hhplus.be.server.queue.domain.repository;
import kr.hhplus.be.server.queue.domain.entity.QueueToken;

import java.util.List;

public interface QueueTokenRepository {
    QueueToken findFirstByToken(String token);
    QueueToken findFirstByUserIdAndStatusNotWithLock(Long userId, QueueToken.Status status);
    List<QueueToken> findQueueTokenEntitiesByStatus(QueueToken.Status status);
    void save(QueueToken queueToken);
}
