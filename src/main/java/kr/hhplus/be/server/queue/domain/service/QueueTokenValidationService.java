package kr.hhplus.be.server.queue.domain.service;

import kr.hhplus.be.server.queue.domain.model.QueueToken;
import kr.hhplus.be.server.queue.domain.repository.QueueTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueueTokenValidationService {

    private final QueueTokenRepository queueTokenRepository;

    @Transactional(readOnly = true)
    public boolean isValidToken(String token) {
        // 토큰이 존재하지 않는 경우 false
        QueueToken queueToken = queueTokenRepository.findFirstByToken(token).orElse(null);
        if (queueToken == null) {
            return false;
        }
        // 토큰이 활성 상태가 아닐 경우 false
        return queueToken.getStatus() == QueueToken.Status.AVAILABLE;
    }
}
