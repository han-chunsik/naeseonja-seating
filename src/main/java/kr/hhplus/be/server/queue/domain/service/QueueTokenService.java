package kr.hhplus.be.server.queue.domain.service;

import kr.hhplus.be.server.queue.domain.dto.QueueTokenPositionResult;
import kr.hhplus.be.server.queue.domain.dto.QueueTokenResult;
import kr.hhplus.be.server.queue.domain.model.QueueToken;
import kr.hhplus.be.server.queue.domain.repository.QueueTokenRepository;
import kr.hhplus.be.server.queue.exception.QueueErrorCode;
import kr.hhplus.be.server.queue.exception.QueueException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueueTokenService {

    private final QueueTokenRepository queueTokenRepository;

    @Transactional
    public QueueTokenResult createToken(Long userId) {
        // 1. 기존 대기열 토큰 존재 여부 확인
        QueueToken queueToken = queueTokenRepository
                .findFirstByUserIdAndStatusNotWithLock(userId, QueueToken.Status.EXPIRED);

        // 2. 기존 토큰 만료
        if (queueToken != null) {
            queueToken.setQueueTokenExpired();
            queueTokenRepository.save(queueToken);
        }
        // 3. 토큰 발급
        QueueToken newQueueToken = QueueToken.createToken(userId);

        // 4. 토큰 저장
        queueTokenRepository.save(newQueueToken);

        return new QueueTokenResult(userId, newQueueToken.getToken());
    }

    @Transactional(readOnly = true)
    public QueueTokenPositionResult getTokenPosition(String token) {
        // 1. 토큰 유효성 검증 - 존재 여부 확인
        QueueToken queueToken = queueTokenRepository.findFirstByToken(token)
                .orElseThrow(() -> new QueueException(QueueErrorCode.TOKEN_NOT_FOUND, token));

        // 2. 토큰 유효성 검증 - 만료 여부 확인
        queueToken.validateExpiredToken();

        // 3. 토큰 순서 조회
        boolean isAvailable = false;
        Long position = null;
        if (queueToken.isWaiting()) {
            List<QueueToken> queueTokens = queueTokenRepository.findQueueTokenEntitiesByStatus(QueueToken.Status.WAITING);
            position = QueueToken.getPositionByToken(queueTokens, token);
        } else {
            isAvailable = queueToken.isAvailable();
        }
        return new QueueTokenPositionResult(token, position, isAvailable);
    }

    @Transactional
    public void expireToken(Long userId) {
        QueueToken queueToken = queueTokenRepository.findFirstByUserIdAndStatusWithLock(userId, QueueToken.Status.AVAILABLE)
                .orElseThrow(() -> new QueueException(QueueErrorCode.USER_TOKEN_NOT_FOUND, userId));
        queueToken.setQueueTokenExpired();
        queueTokenRepository.save(queueToken);
    }

}
