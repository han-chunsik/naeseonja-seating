package kr.hhplus.be.server.queue.presentation.scheduler;

import kr.hhplus.be.server.queue.domain.service.QueueTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueueTokenScheduler {

    private final QueueTokenService queueTokenService;

    @Scheduled(fixedDelay = 1000)
    public void QueueTokenStatusChange() {
        // 1. 삭제 대상 토큰 삭제(등록된지 6시간, 활성화된 후 10분 경과, 만료 상태)
        queueTokenService.deleteExpiredToken();
        // 2. 토큰 활성
        queueTokenService.activateTokens();
    }
}
