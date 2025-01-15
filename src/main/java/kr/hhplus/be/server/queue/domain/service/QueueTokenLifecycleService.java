package kr.hhplus.be.server.queue.domain.service;

import kr.hhplus.be.server.queue.domain.model.QueueToken;
import kr.hhplus.be.server.queue.domain.repository.QueueTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QueueTokenLifecycleService {

    private final QueueTokenRepository queueTokenRepository;

    private static final int WAITING_TOKEN_EXPIRATION_HOURS = QueueTokenLimit.WAITING_TOKEN_EXPIRATION_TIME_HOUR.getLimit();
    private static final int ACTIVE_TOKEN_EXPIRATION_MINUTES = QueueTokenLimit.ACTIVE_TOKEN_EXPIRATION_TIME_MINUTE.getLimit();
    private static final int ACTIVE_QUEUE_LIMIT = QueueTokenLimit.ACTIVE_QUEUE_LIMIT.getLimit();

    @Transactional
    public void deleteExpiredToken() {
        LocalDateTime sixHoursAgo = LocalDateTime.now().minusHours(WAITING_TOKEN_EXPIRATION_HOURS);
        LocalDateTime tenMinutesAgo = LocalDateTime.now().minusMinutes(ACTIVE_TOKEN_EXPIRATION_MINUTES);

        List<QueueToken> expiredTokens = queueTokenRepository
                .findQueueTokenEntitiesByCreatedAtBeforeOrActivatedAtBeforeOrStatus(sixHoursAgo, tenMinutesAgo, QueueToken.Status.EXPIRED);

        if (!expiredTokens.isEmpty()) {
            queueTokenRepository.deleteAll(expiredTokens);
        }
    }

    @Transactional
    public void activateTokens() {
        int activeTokenCount = queueTokenRepository.findQueueTokenEntitiesByStatus(QueueToken.Status.AVAILABLE).size();
        int availableSlots = ACTIVE_QUEUE_LIMIT - activeTokenCount;


        if (availableSlots > 0){
            Pageable pageable = PageRequest.of(0, availableSlots);
            List<QueueToken> queueTokens = queueTokenRepository.findQueueTokenEntitiesByStatusPageable(QueueToken.Status.WAITING, pageable);

            queueTokens.forEach(QueueToken::setQueueTokenActivated);
            queueTokenRepository.saveAll(queueTokens);
        }
    }
}
