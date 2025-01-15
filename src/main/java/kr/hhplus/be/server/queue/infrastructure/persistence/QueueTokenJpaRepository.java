package kr.hhplus.be.server.queue.infrastructure.persistence;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.queue.infrastructure.jpa.QueueTokenEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface QueueTokenJpaRepository extends JpaRepository<QueueTokenEntity, Long> {
    Optional<QueueTokenEntity> findFirstByToken(String token);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT q FROM QueueTokenEntity q WHERE q.userId = :userId AND q.status != :status ORDER BY q.id ASC")
    QueueTokenEntity findFirstByUserIdAndStatusNotWithLock(@Param("userId") Long userId, @Param("status") QueueTokenEntity.Status status);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT q FROM QueueTokenEntity q WHERE q.userId = :userId AND q.status = :status ORDER BY q.id ASC")
    Optional<QueueTokenEntity> findFirstByUserIdAndStatusWithLock(@Param("userId") Long userId, @Param("status") QueueTokenEntity.Status status);

    List<QueueTokenEntity> findQueueTokenEntitiesByCreatedAtBeforeOrActivatedAtBeforeOrStatus(LocalDateTime createdBefore, LocalDateTime activatedBefore, QueueTokenEntity.Status status);

    List<QueueTokenEntity> findQueueTokenEntitiesByStatus(QueueTokenEntity.Status status);

    @Query("SELECT q FROM QueueTokenEntity q WHERE q.status = :status")
    List<QueueTokenEntity> findQueueTokenEntitiesByStatusPageable(@Param("status") QueueTokenEntity.Status status, Pageable pageable);
}
