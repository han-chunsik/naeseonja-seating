package kr.naeseonja.be.server.reservation.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservationTemporaryResult {
    private Long reservationId;
    private Long seatId;
    private Long userId;
    private Long price;

    public ReservationTemporaryResult(Long reservationId, Long seatId, Long userId, Long price) {
        this.reservationId = reservationId;
        this.seatId = seatId;
        this.userId = userId;
        this.price = price;
    }
}
