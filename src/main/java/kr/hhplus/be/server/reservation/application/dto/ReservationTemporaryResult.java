package kr.hhplus.be.server.reservation.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservationTemporaryResult {
    private Long seatId;
    private Long userId;
    private Long price;

    public ReservationTemporaryResult(Long seatId, Long userId, Long price) {
        this.seatId = seatId;
        this.userId = userId;
        this.price = price;
    }
}
