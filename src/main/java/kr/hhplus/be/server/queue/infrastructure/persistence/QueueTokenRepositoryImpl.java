package kr.hhplus.be.server.queue.infrastructure.persistence;

import kr.hhplus.be.server.queue.domain.model.QueueToken;
import kr.hhplus.be.server.queue.domain.repository.QueueTokenRepository;
import kr.hhplus.be.server.queue.infrastructure.jpa.QueueTokenEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class QueueTokenRepositoryImpl implements QueueTokenRepository {

    private final QueueTokenJpaRepository queueTokenJpaRepository;

    @Override
    public void deleteAll(List<QueueToken> queueTokens) {
        List<QueueTokenEntity> entities = queueTokens.stream()
                .map(QueueToken::toEntity)
                .collect(Collectors.toList());
        queueTokenJpaRepository.deleteAll(entities);
    }

    @Override
    public void saveAll(List<QueueToken> queueTokens) {
        List<QueueTokenEntity> entities = queueTokens.stream()
                .map(QueueToken::toEntity)
                .collect(Collectors.toList());
        queueTokenJpaRepository.saveAll(entities);
    }

    @Override
    public List<QueueToken> findQueueTokenEntitiesByCreatedAtBeforeOrActivatedAtBeforeOrStatus(LocalDateTime createdBefore, LocalDateTime activatedBefore, QueueToken.Status status) {
        List<QueueTokenEntity> entities = queueTokenJpaRepository.findQueueTokenEntitiesByCreatedAtBeforeOrActivatedAtBeforeOrStatus(createdBefore, activatedBefore, QueueTokenEntity.Status.valueOf(status.name()));
        return entities.stream()
                .map(QueueToken::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<QueueToken> findQueueTokenEntitiesByStatusPageable(QueueToken.Status status, Pageable pageable) {
        List<QueueTokenEntity> entities = queueTokenJpaRepository.findQueueTokenEntitiesByStatusPageable(QueueTokenEntity.Status.valueOf(status.name()), pageable);

        return entities.stream()
                .map(QueueToken::fromEntity)  // 엔티티 -> 도메인 변환
                .collect(Collectors.toList());
    }

    @Override
    public List<QueueToken> findQueueTokenEntitiesByStatus(QueueToken.Status status) {
        List<QueueTokenEntity> entities = queueTokenJpaRepository.findQueueTokenEntitiesByStatus(QueueTokenEntity.Status.valueOf(status.name()));

        return entities.stream()
                .map(QueueToken::fromEntity)  // 엔티티 -> 도메인 변환
                .collect(Collectors.toList());
    }

    @Override
    public QueueToken findFirstByUserIdAndStatusNotWithLock(Long userId, QueueToken.Status status) {
        QueueTokenEntity entity = queueTokenJpaRepository.findFirstByUserIdAndStatusNotWithLock(userId, QueueTokenEntity.Status.valueOf(status.name()));
        return (entity != null) ? QueueToken.fromEntity(entity) : null;
    }

    @Override
    public Optional<QueueToken> findFirstByUserIdAndStatusWithLock(Long userId, QueueToken.Status status) {
        Optional<QueueTokenEntity> entity = queueTokenJpaRepository.findFirstByUserIdAndStatusWithLock(userId, QueueTokenEntity.Status.valueOf(status.name()));
        return entity.map(QueueToken::fromEntity);
    }

    @Override
    public Optional<QueueToken> findFirstByToken(String token) {
        Optional<QueueTokenEntity> entity = queueTokenJpaRepository.findFirstByToken(token);
        return entity.map(QueueToken::fromEntity);
    }


    @Override
    public void save(QueueToken queueToken) {
        QueueTokenEntity entity = queueToken.toEntity();
        queueTokenJpaRepository.save(entity);
    }
}
