package kr.hhplus.be.server.reservation.application.service;

import jakarta.transaction.Transactional;
import kr.hhplus.be.server.reservation.domain.event.ConfirmDataSentEvent;
import kr.hhplus.be.server.reservation.domain.event.ConfirmDataSentEventPublisher;
import kr.hhplus.be.server.reservation.domain.outbox.ReservationOutbox;
import kr.hhplus.be.server.reservation.domain.outbox.ReservationOutboxRepository;
import kr.hhplus.be.server.reservation.domain.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ReservationOutboxService {
    private final ReservationOutboxRepository reservationOutboxRepository;
    private final ConfirmDataSentEventPublisher confirmDataSentEventPublisher;
    private final ReservationService reservationService;

    @Transactional
    public void save(ReservationOutbox reservationOutbox) {
        reservationOutboxRepository.save(reservationOutbox);
    }

    @Transactional
    public void published(String reservationId) {
        ReservationOutbox outbox = reservationOutboxRepository.findByPayload(reservationId);
        outbox.setStatusPublished();
        reservationOutboxRepository.save(outbox);
    }

    @Transactional
    public void unpublished() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime fiveSecondsAgo = now.minusSeconds(5);
        List<ReservationOutbox> outboxList = reservationOutboxRepository.findAllByStatusAndUpdatedAtBefore(ReservationOutbox.Status.INIT, fiveSecondsAgo);

        // Todo) 추 후 직렬화로 데이터를 payload에 넣으면 그걸로 재발행
        outboxList.forEach(outbox -> {
            int retryCount = outbox.getRetryCount();

            if (retryCount < 4) {
                Long reservationId = Long.parseLong(outbox.getPayload());
                ConfirmDataSentEvent event = ConfirmDataSentEvent.fromDomain(reservationService.getReservation(reservationId));
                confirmDataSentEventPublisher.send(event);
                outbox.setRetryCount();
                outbox.setDelaySeconds(retryCount);
                reservationOutboxRepository.save(outbox);
            } else {
                outbox.setRetryCount();
                outbox.setStatusFailed();
                reservationOutboxRepository.save(outbox);
            }
        });
    }
}
