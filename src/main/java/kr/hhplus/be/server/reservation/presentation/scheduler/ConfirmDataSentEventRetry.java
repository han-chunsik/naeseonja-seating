package kr.hhplus.be.server.reservation.presentation.scheduler;

import kr.hhplus.be.server.reservation.application.service.ReservationOutboxService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConfirmDataSentEventRetry {

    private final ReservationOutboxService reservationOutboxService;

    @Scheduled(fixedDelay = 1000)
    public void retry() {
        reservationOutboxService.unpublished();
    }
}
