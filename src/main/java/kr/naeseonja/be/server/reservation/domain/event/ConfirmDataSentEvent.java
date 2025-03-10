package kr.naeseonja.be.server.reservation.domain.event;

import kr.naeseonja.be.server.reservation.domain.model.Reservation;
import kr.naeseonja.be.server.reservation.domain.outbox.ReservationOutbox;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ConfirmDataSentEvent {
    private Long id;
    private Long seatId;
    private Long userId;
    private Long price;
    private LocalDateTime createdAt;
    private LocalDateTime reservedAt;

    public ConfirmDataSentEvent(Long id, Long seatId, Long userId, Long price, LocalDateTime createdAt, LocalDateTime reservedAt) {
        this.id = id;
        this.seatId = seatId;
        this.userId = userId;
        this.price = price;
        this.createdAt = createdAt;
        this.reservedAt = reservedAt;
    }

    public static ConfirmDataSentEvent fromDomain(Reservation reservation) {
        return new ConfirmDataSentEvent(
                reservation.getId(),
                reservation.getSeatId(),
                reservation.getUserId(),
                reservation.getPrice(),
                reservation.getCreatedAt(),
                reservation.getReservedAt()
        );
    }

    public ReservationOutbox toOutboxEntity(ConfirmDataSentEvent event) {
        String eventType = "ConfirmDataSent";
        String payload = event.getId().toString();
        ReservationOutbox.Status outBoxStatus = ReservationOutbox.Status.INIT;
        return new ReservationOutbox(eventType, payload, outBoxStatus);
    }
}
