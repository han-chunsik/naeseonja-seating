package kr.hhplus.be.server.reservation.event;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReservationEvent {
    private Long id;
    private Long seatId;
    private Long userId;
    private Long price;
    private LocalDateTime createdAt;
    private LocalDateTime reservedAt;

    public ReservationEvent(Long id, Long seatId, Long userId, Long price, LocalDateTime createdAt, LocalDateTime reservedAt) {
        this.id = id;
        this.seatId = seatId;
        this.userId = userId;
        this.price = price;
        this.createdAt = createdAt;
        this.reservedAt = reservedAt;
    }
}
