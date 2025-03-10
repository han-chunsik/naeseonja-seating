package kr.naeseonja.be.server.queue.domain.service;

import kr.naeseonja.be.server.queue.domain.dto.QueueTokenPositionResult;
import kr.naeseonja.be.server.queue.domain.dto.QueueTokenResult;
import kr.naeseonja.be.server.queue.domain.model.QueueToken;
import kr.naeseonja.be.server.queue.domain.repository.QueueTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueueTokenService {

    private final QueueTokenRepository queueTokenRedisRepository;
    private final QueueTokenRepository queueTokenRepository;

    @Transactional
    public QueueTokenResult createToken(Long userId) {
        // 토큰 발급
        QueueToken newQueueToken = QueueToken.createToken(userId);
        // 저장
        queueTokenRedisRepository.createToken(newQueueToken);
        return new QueueTokenResult(userId, newQueueToken.getToken());
    }

    @Transactional(readOnly = true)
    public QueueTokenPositionResult getTokenPosition(String token) {
        // 토큰 활성 여부 확인
        boolean isAvailable = queueTokenRedisRepository.isTokenActive(token);

        // 활성화 되어 있지 않은 경우 순서 조회
        Long position = 0L;
        if (!isAvailable) {
            position = queueTokenRedisRepository.getTokenPosition(token).orElse(0L);
        }
        return new QueueTokenPositionResult(token, position, isAvailable);
    }

    @Transactional
    public void expireToken(Long userId) {
        // 활성 토큰에서 삭제
        // (todo)
    }

}
