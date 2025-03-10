package kr.naeseonja.be.server.queue.domain.service;

import kr.naeseonja.be.server.queue.domain.repository.QueueTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueueTokenLifecycleService {

    private final QueueTokenRepository queueTokenRedisRepository;

    private static final int WAITING_TOKEN_EXPIRATION_HOURS = QueueTokenLimit.WAITING_TOKEN_EXPIRATION_TIME_HOUR.getLimit();
    private static final int ACTIVE_TOKEN_EXPIRATION_MINUTES = QueueTokenLimit.ACTIVE_TOKEN_EXPIRATION_TIME_MINUTE.getLimit();
    private static final int ACTIVE_QUEUE_LIMIT = QueueTokenLimit.ACTIVE_QUEUE_LIMIT.getLimit();

    @Transactional
    public void deleteExpiredToken() {
        // (todo)
    }

    @Transactional
    public void activateTokens() {
        queueTokenRedisRepository.activateTokens(ACTIVE_QUEUE_LIMIT);
    }
}
