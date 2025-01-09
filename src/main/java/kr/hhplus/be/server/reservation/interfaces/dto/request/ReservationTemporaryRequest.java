package kr.hhplus.be.server.reservation.interfaces.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservationTemporaryRequest {
    private Long seatId;
    private Long userId;
    private Long price;

    @Builder
    public ReservationTemporaryRequest(Long seatId, Long userId, Long price) {
        this.seatId = seatId;
        this.userId = userId;
        this.price = price;
    }
}
