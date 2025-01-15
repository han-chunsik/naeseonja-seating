package kr.hhplus.be.server.reservation.presentation.dto.response;

import kr.hhplus.be.server.reservation.application.dto.ReservationResult;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservationResponse {
    private Long reservationId;
    private Long userId;
    private Long price;

    public ReservationResponse(Long reservationId, Long userId, Long price) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.price = price;
    }

    public static ReservationResponse from(ReservationResult reservationResult) {
        return new ReservationResponse(reservationResult.getReservationId(), reservationResult.getUserId(), reservationResult.getPrice());
    }
}
