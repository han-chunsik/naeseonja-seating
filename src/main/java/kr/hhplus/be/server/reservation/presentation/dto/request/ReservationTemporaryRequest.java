package kr.hhplus.be.server.reservation.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservationTemporaryRequest {
    private Long seatId;
    private Long userId;

    public ReservationTemporaryRequest(Long seatId, Long userId) {
        this.seatId = seatId;
        this.userId = userId;
    }
}
