package kr.hhplus.be.server.reservation.infrastructure.kafka;

import kr.hhplus.be.server.reservation.domain.event.ConfirmDataSentEvent;
import kr.hhplus.be.server.reservation.domain.event.ConfirmDataSentEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConfirmDataSentKafkaEventPublisher implements ConfirmDataSentEventPublisher {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC = "ConfirmDataSent";

    @Override
    public void send(ConfirmDataSentEvent event) {
        String eventId = event.getId().toString();
        kafkaTemplate.send(TOPIC, eventId, eventId);
    }
}
