package kr.hhplus.be.server.reservation.interfaces.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReservationTemporaryResponse {
    private Long seatId;
    private Long userId;
    private Long price;
    private LocalDateTime createdAt;

    public ReservationTemporaryResponse(Long seatId, Long userId, Long price, LocalDateTime createdAt) {
        this.seatId = seatId;
        this.userId = userId;
        this.price = price;
        this.createdAt = createdAt;
    }
}
