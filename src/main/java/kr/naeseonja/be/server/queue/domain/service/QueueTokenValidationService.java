package kr.naeseonja.be.server.queue.domain.service;

import kr.naeseonja.be.server.queue.domain.repository.QueueTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueueTokenValidationService {

    private final QueueTokenRepository queueTokenRedisRepository;

    @Transactional(readOnly = true)
    public boolean isValidToken(String token) {
        // 토큰이 없거나 활성화 되어있지 않으면 false, 활성화 되어있으면 true
        return queueTokenRedisRepository.isTokenActive(token);
    }
}
