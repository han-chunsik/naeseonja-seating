package kr.hhplus.be.server.reservation.application.event;

import jakarta.transaction.Transactional;
import kr.hhplus.be.server.reservation.domain.event.ConfirmDataSentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void confirmDataSentPublish(ConfirmDataSentEvent event) {
        eventPublisher.publishEvent(event);
    }
}