package kr.naeseonja.be.server.reservation.presentation.consumer;

import kr.naeseonja.be.server.reservation.application.service.ReservationOutboxService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConfirmDataSentConsumer {

    private final ReservationOutboxService reservationOutboxService;

    @KafkaListener(topics = "ConfirmDataSent", groupId = "concert")
    public void listener(ConsumerRecord<String, Object> data) {
        reservationOutboxService.published(data.key());
    }
}
