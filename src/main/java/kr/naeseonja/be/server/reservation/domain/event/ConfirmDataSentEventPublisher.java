package kr.naeseonja.be.server.reservation.domain.event;

import org.springframework.stereotype.Component;

@Component
public interface ConfirmDataSentEventPublisher {
    void send(ConfirmDataSentEvent event);
}
