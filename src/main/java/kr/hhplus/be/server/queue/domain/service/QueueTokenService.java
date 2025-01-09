package kr.hhplus.be.server.queue.domain.service;

import kr.hhplus.be.server.queue.domain.dto.QueueTokenPositionResult;
import kr.hhplus.be.server.queue.domain.dto.QueueTokenResult;
import kr.hhplus.be.server.queue.domain.entity.QueueToken;
import kr.hhplus.be.server.queue.domain.repository.QueueTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QueueTokenService {

    private final QueueTokenRepository queueTokenRepository;

    @Transactional
    public QueueTokenResult createToken(Long userId) {
        // 1. 기존 대기열 토큰 존재 여부 확인
        QueueToken queueToken = queueTokenRepository.findFirstByUserIdAndStatusNotWithLock(userId, QueueToken.Status.EXPIRED);
        // 2. 기존 토큰 만료
        if (queueToken != null) {
            queueToken.changeStatus(QueueToken.Status.EXPIRED);
            queueTokenRepository.save(queueToken);
        }
        // 3. 토큰 발급
        String newToken = UUID.randomUUID().toString();

        QueueToken newQueueToken = new QueueToken(
                null,
                userId,
                newToken,
                QueueToken.Status.WAITING,
                LocalDateTime.now(),
                null
        );

        // 4. 토큰 저장
        queueTokenRepository.save(newQueueToken);

        return QueueTokenResult.builder()
                .userId(userId)
                .token(newToken)
                .build();
    }

    @Transactional(readOnly = true)
    public QueueTokenPositionResult getTokenPosition(String token) {
        // 1. 토큰 유효성 검증
        QueueToken queueToken = Optional.ofNullable(queueTokenRepository.findFirstByToken(token))
                .orElseThrow(() -> new RuntimeException("유효하지 않은 토큰입니다."));

        if (queueToken.getStatus() == QueueToken.Status.EXPIRED) {
            throw new RuntimeException("만료된 토큰입니다.");
        }

        boolean isAvailable = false;
        Long position = null;
        if (queueToken.getStatus() == QueueToken.Status.WAITING) {
            List<QueueToken> queueTokens = queueTokenRepository.findQueueTokenEntitiesByStatus(QueueToken.Status.WAITING);
            position = (long) queueTokens.stream()
                    .map(QueueToken::getToken)
                    .toList()
                    .indexOf(token) + 1;
        } else {
            isAvailable = true;
        }
        return QueueTokenPositionResult.builder()
                .token(token)
                .position(position)
                .isAvailable(isAvailable)
                .build();
    }

    @Transactional
    public void deleteExpiredToken() {
        LocalDateTime sixHoursAgo = LocalDateTime.now().minusHours(6);
        LocalDateTime tenMinutesAgo = LocalDateTime.now().minusMinutes(10);

        List<QueueToken> tokensToDelete = queueTokenRepository
                .findQueueTokenEntitiesByCreatedAtBeforeOrActivatedAtBeforeOrStatus(sixHoursAgo, tenMinutesAgo, QueueToken.Status.EXPIRED);

        if (!tokensToDelete.isEmpty()) {
            queueTokenRepository.deleteAll(tokensToDelete);
        }
    }

    @Transactional
    public void activateTokens() {
        int activeTokenCount = queueTokenRepository.findQueueTokenEntitiesByStatus(QueueToken.Status.AVAILABLE).size();
        int availableSlots = QueueTokenLimit.ACTIVE_QUEUE_LIMIT.getLimit() - activeTokenCount;

        LocalDateTime currentDateTime = LocalDateTime.now();

        if (availableSlots > 0){
            Pageable pageable = PageRequest.of(0, availableSlots);
            List<QueueToken> queueTokens = queueTokenRepository.findQueueTokenEntitiesByStatusPageable(QueueToken.Status.WAITING, pageable);

            queueTokens.forEach(queueToken -> queueToken.activatedStatus(currentDateTime));
            queueTokenRepository.saveAll(queueTokens);
        }
    }

    @Transactional(readOnly = true)
    public boolean isValidToken(String token) {
        // 토큰이 존재하지 않는 경우 false
        QueueToken queueToken = queueTokenRepository.findFirstByToken(token);
        if (queueToken == null) {
            return false;
        }

        // 토큰이 활성 상태가 아닐 경우 false
        return queueToken.getStatus() == QueueToken.Status.AVAILABLE;
    }

    @Transactional
    public void expireToken(Long userId) {
        QueueToken queueToken = queueTokenRepository.findFirstByUserIdAndStatusNotWithLock(userId, QueueToken.Status.AVAILABLE);
        queueToken.changeStatus(QueueToken.Status.EXPIRED);
        queueTokenRepository.save(queueToken);
    }

}
