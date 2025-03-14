package kr.naeseonja.be.server.queue.domain.repository;

import kr.naeseonja.be.server.queue.domain.model.QueueToken;

import java.util.Optional;

public interface QueueTokenRepository {
    boolean isTokenActive(String token);
    QueueToken createToken(QueueToken queueToken); // 토큰 발급
    Optional<Long> getTokenPosition(String token); // 토큰 순서 조회
    void activateTokens(int activeQueueLimit);
}
