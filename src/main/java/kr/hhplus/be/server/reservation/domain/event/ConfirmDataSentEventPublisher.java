package kr.hhplus.be.server.reservation.domain.event;

import org.springframework.stereotype.Component;

@Component
public interface ConfirmDataSentEventPublisher {
    void send(ConfirmDataSentEvent event);
}
