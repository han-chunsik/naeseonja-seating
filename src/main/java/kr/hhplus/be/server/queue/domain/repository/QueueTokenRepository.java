package kr.hhplus.be.server.queue.domain.repository;
import kr.hhplus.be.server.queue.domain.entity.QueueToken;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface QueueTokenRepository {
    QueueToken findFirstByToken(String token);
    QueueToken findFirstByUserIdAndStatusNotWithLock(Long userId, QueueToken.Status status);
    List<QueueToken> findQueueTokenEntitiesByStatus(QueueToken.Status status);
    List<QueueToken> findQueueTokenEntitiesByStatusPageable(QueueToken.Status status, Pageable pageable);
    List<QueueToken> findQueueTokenEntitiesByCreatedAtBeforeOrActivatedAtBeforeOrStatus(LocalDateTime createdBefore, LocalDateTime activatedBefore, QueueToken.Status status);
    void save(QueueToken queueToken);
    void deleteAll(List<QueueToken> queueTokens);
    void saveAll(List<QueueToken> queueTokens);
}
