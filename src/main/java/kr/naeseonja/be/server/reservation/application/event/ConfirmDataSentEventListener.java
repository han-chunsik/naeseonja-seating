package kr.naeseonja.be.server.reservation.application.event;

import kr.naeseonja.be.server.reservation.application.service.ReservationOutboxService;
import kr.naeseonja.be.server.reservation.domain.event.ConfirmDataSentEvent;
import kr.naeseonja.be.server.reservation.domain.event.ConfirmDataSentEventPublisher;
import kr.naeseonja.be.server.reservation.domain.outbox.ReservationOutbox;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;
import static org.springframework.transaction.event.TransactionPhase.BEFORE_COMMIT;

@Slf4j
@RequiredArgsConstructor
@Component
public class ConfirmDataSentEventListener {

    private final ReservationOutboxService reservationOutboxService;
    private final ConfirmDataSentEventPublisher confirmDataSentEventPublisher;

    @TransactionalEventListener(phase = BEFORE_COMMIT)
    public void saveOutbox(ConfirmDataSentEvent event) {
        ReservationOutbox outbox = event.toOutboxEntity(event);
        reservationOutboxService.save(outbox);
    }

    @TransactionalEventListener(phase = AFTER_COMMIT)
    public void sendOrderInfo(ConfirmDataSentEvent reservationEvent) {
        confirmDataSentEventPublisher.send(reservationEvent);
    }
}
