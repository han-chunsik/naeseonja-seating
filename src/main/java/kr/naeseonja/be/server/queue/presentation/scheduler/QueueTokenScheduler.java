package kr.naeseonja.be.server.queue.presentation.scheduler;

import kr.naeseonja.be.server.queue.domain.service.QueueTokenLifecycleService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueueTokenScheduler {

    private final QueueTokenLifecycleService queueTokenLifecycleService;

    @Scheduled(fixedDelay = 1000)
    public void QueueTokenStatusChange() {
        // 1. 삭제 대상 토큰 삭제(등록된지 6시간, 활성화된 후 10분 경과, 만료 상태)
        queueTokenLifecycleService.deleteExpiredToken();
        // 2. 토큰 활성
        queueTokenLifecycleService.activateTokens();
    }
}
