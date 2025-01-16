package kr.hhplus.be.server.reservation.presentation.scheduler;

import kr.hhplus.be.server.reservation.application.TemporaryReservationExpired;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TemporaryReservationScheduler {

    private final TemporaryReservationExpired temporaryReservationExpired;

    @Scheduled(fixedDelay = 1000)
    public void tempReservationExpired() {
        // 1. 만료 대상 (임시예약 생성된지 5분 경과 시)
        temporaryReservationExpired.TempReservationExpired();
    }
}
