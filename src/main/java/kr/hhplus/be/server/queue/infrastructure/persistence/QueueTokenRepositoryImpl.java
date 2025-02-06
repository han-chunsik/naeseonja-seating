package kr.hhplus.be.server.queue.infrastructure.persistence;

import kr.hhplus.be.server.queue.domain.model.QueueToken;
import kr.hhplus.be.server.queue.domain.repository.QueueTokenRepository;
import kr.hhplus.be.server.queue.infrastructure.jpa.QueueTokenEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class QueueTokenRepositoryImpl implements QueueTokenRepository {

    private final QueueTokenJpaRepository queueTokenJpaRepository;



    @Override
    public boolean isTokenActive(String token) {
        return false;
    }

    @Override
    public QueueToken createToken(QueueToken queueToken) {
        return null;
    }

    @Override
    public Optional<Long> getTokenPosition(String token) {
        return Optional.of(0L);
    }

    @Override
    public void activateTokens(int activeQueueLimit) {

    }

    @Override
    public QueueToken findFirstByUserIdAndStatusNotWithLock(Long userId, QueueToken.Status status) {
        QueueTokenEntity entity = queueTokenJpaRepository.findFirstByUserIdAndStatusNotWithLock(userId, QueueTokenEntity.Status.valueOf(status.name()));
        return (entity != null) ? QueueToken.fromEntity(entity) : null;
    }


    @Override
    public void save(QueueToken queueToken) {
        QueueTokenEntity entity = queueToken.toEntity();
        queueTokenJpaRepository.save(entity);
    }
}
