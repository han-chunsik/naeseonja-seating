package kr.naeseonja.be.server.reservation.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservationResult {
    private Long reservationId;
    private Long userId;
    private Long price;

    public ReservationResult(Long reservationId, Long userId, Long price) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.price = price;
    }
}
