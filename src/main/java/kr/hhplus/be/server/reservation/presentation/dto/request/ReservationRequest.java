package kr.hhplus.be.server.reservation.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservationRequest {
    private Long reservationId;
    private Long userId;

    public ReservationRequest(Long reservationId, Long userId) {
        this.reservationId = reservationId;
        this.userId = userId;
    }
}
