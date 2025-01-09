package kr.hhplus.be.server.queue.infrastructure.persistence;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.queue.infrastructure.jpa.QueueTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface QueueTokenJpaRepository extends JpaRepository<QueueTokenEntity, Long> {
    QueueTokenEntity findFirstByToken(String token);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT e FROM QueueTokenEntity e WHERE e.userId = :userId AND e.status != :status ORDER BY e.id ASC")
    QueueTokenEntity findFirstByUserIdAndStatusNotWithLock(@Param("userId") Long userId, @Param("status") QueueTokenEntity.Status status);
    List<QueueTokenEntity> findQueueTokenEntitiesByStatus(QueueTokenEntity.Status status);
}
