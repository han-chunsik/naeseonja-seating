package kr.hhplus.be.server.queue.infrastructure.persistence;

import kr.hhplus.be.server.queue.domain.entity.QueueToken;
import kr.hhplus.be.server.queue.domain.repository.QueueTokenRepository;
import kr.hhplus.be.server.queue.infrastructure.jpa.QueueTokenEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class QueueTokenRepositoryImpl implements QueueTokenRepository {

    private final QueueTokenJpaRepository queueTokenJpaRepository;

    // To-Do: ModelMapper로 변경

    @Override
    public void deleteAll(List<QueueToken> queueTokens) {
        List<QueueTokenEntity> entities = queueTokens.stream()
            .map(queueToken -> new QueueTokenEntity(
               queueToken.getId(),
               queueToken.getUserId(),
               queueToken.getToken(),
               QueueTokenEntity.Status.valueOf(queueToken.getStatus().name()),
               queueToken.getCreatedAt(),
               queueToken.getActivatedAt()
            )).collect(Collectors.toList());
        queueTokenJpaRepository.deleteAll(entities);
    }

    @Override
    public void saveAll(List<QueueToken> queueTokens) {
        List<QueueTokenEntity> entities = queueTokens.stream()
                .map(queueToken -> new QueueTokenEntity(
                        queueToken.getId(),
                        queueToken.getUserId(),
                        queueToken.getToken(),
                        QueueTokenEntity.Status.valueOf(queueToken.getStatus().name()),
                        queueToken.getCreatedAt(),
                        queueToken.getActivatedAt()
                )).collect(Collectors.toList());
        queueTokenJpaRepository.saveAll(entities);
    }

    @Override
    public List<QueueToken> findQueueTokenEntitiesByCreatedAtBeforeOrActivatedAtBeforeOrStatus(LocalDateTime createdBefore, LocalDateTime activatedBefore, QueueToken.Status status) {
        List<QueueTokenEntity> entities = queueTokenJpaRepository.findQueueTokenEntitiesByCreatedAtBeforeOrActivatedAtBeforeOrStatus(createdBefore, activatedBefore, QueueTokenEntity.Status.valueOf(status.name()));
        return entities.stream()
            .map(entity -> new QueueToken(
            entity.getId(),
            entity.getUserId(),
            entity.getToken(),
            QueueToken.Status.valueOf(entity.getStatus().name()),
            entity.getCreatedAt(),
            entity.getActivatedAt()))
            .collect(Collectors.toList());
    }

    @Override
    public List<QueueToken> findQueueTokenEntitiesByStatusPageable(QueueToken.Status status, Pageable pageable) {
        List<QueueTokenEntity> entities = queueTokenJpaRepository.findQueueTokenEntitiesByStatusPageable(QueueTokenEntity.Status.valueOf(status.name()), pageable);

        return entities.stream()
                .map(entity -> new QueueToken(
                        entity.getId(),
                        entity.getUserId(),
                        entity.getToken(),
                        QueueToken.Status.valueOf(entity.getStatus().name()),
                        entity.getCreatedAt(),
                        entity.getActivatedAt()))
                .collect(Collectors.toList());
    }

    @Override
    public List<QueueToken> findQueueTokenEntitiesByStatus(QueueToken.Status status) {
        List<QueueTokenEntity> entities = queueTokenJpaRepository.findQueueTokenEntitiesByStatus(QueueTokenEntity.Status.valueOf(status.name()));

        return entities.stream()
                .map(entity -> new QueueToken(
                        entity.getId(),
                        entity.getUserId(),
                        entity.getToken(),
                        QueueToken.Status.valueOf(entity.getStatus().name()),
                        entity.getCreatedAt(),
                        entity.getActivatedAt()))
                .collect(Collectors.toList());
    }

    @Override
    public QueueToken findFirstByUserIdAndStatusNotWithLock(Long userId, QueueToken.Status status) {
        QueueTokenEntity entity = queueTokenJpaRepository.findFirstByUserIdAndStatusNotWithLock(userId, QueueTokenEntity.Status.valueOf(status.name()));
        return (entity != null) ? new QueueToken(
                entity.getId(),
                entity.getUserId(),
                entity.getToken(),
                QueueToken.Status.valueOf(entity.getStatus().name()),
                entity.getCreatedAt(),
                entity.getActivatedAt()
        ) : null;
    }

    @Override
    public QueueToken findFirstByToken(String token) {
        QueueTokenEntity entity = queueTokenJpaRepository.findFirstByToken(token);
        // converting
        return (entity != null) ? new QueueToken(
                entity.getId(),
                entity.getUserId(),
                entity.getToken(),
                QueueToken.Status.valueOf(entity.getStatus().name()),
                entity.getCreatedAt(),
                entity.getActivatedAt()
        ) : null;
    }


    @Override
    public void save(QueueToken queueToken) {
        // converting
        QueueTokenEntity entity = QueueTokenEntity.builder()
                .id(queueToken.getId())
                .userId(queueToken.getUserId())
                .token(queueToken.getToken())
                .status(QueueTokenEntity.Status.valueOf(queueToken.getStatus().name()))
                .createdAt(queueToken.getCreatedAt())
                .activatedAt(queueToken.getActivatedAt())
                .build();

        queueTokenJpaRepository.save(entity);
    }
}
