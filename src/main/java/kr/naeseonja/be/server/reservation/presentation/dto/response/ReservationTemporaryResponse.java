package kr.naeseonja.be.server.reservation.presentation.dto.response;

import kr.naeseonja.be.server.reservation.application.dto.ReservationTemporaryResult;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservationTemporaryResponse {
    private Long reservationId;
    private Long seatId;
    private Long userId;
    private Long price;

    public ReservationTemporaryResponse(Long reservationId, Long seatId, Long userId, Long price) {
        this.reservationId = reservationId;
        this.seatId = seatId;
        this.userId = userId;
        this.price = price;
    }

    public static ReservationTemporaryResponse from(ReservationTemporaryResult reservationTemporaryResult) {
        return new ReservationTemporaryResponse(reservationTemporaryResult.getReservationId(), reservationTemporaryResult.getSeatId(), reservationTemporaryResult.getUserId(), reservationTemporaryResult.getPrice());
    }
}
