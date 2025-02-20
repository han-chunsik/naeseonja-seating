package kr.hhplus.be.server.reservation.application.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReservationEventListener {
    @Async
    @EventListener
    public void sendPush(ReservationEvent reservationEvent) throws InterruptedException {
        log.info("데이터 플랫폼 호출 [ReservationId : {}, ReservedAt {}]", reservationEvent.getId(), reservationEvent.getReservedAt());
    }
}
