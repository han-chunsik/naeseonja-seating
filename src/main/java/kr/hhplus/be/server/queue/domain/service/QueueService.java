package kr.hhplus.be.server.queue.domain.service;

import kr.hhplus.be.server.queue.domain.dto.QueueTokenResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QueueService {

    public QueueTokenResult createToken(Long userId) {
        // 1. 기존 대기열 토큰 존재 여부 확인
        // 2. 기존 토큰 만료
        // 3. 토큰 발급
        // 4. 토큰 저장
        String newToken = UUID.randomUUID().toString();
        return QueueTokenResult.builder()
                .userId(userId)
                .token(newToken)
                .build();
    }
}
