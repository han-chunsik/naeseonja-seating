package kr.hhplus.be.server.reservation.presentation.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservationTemporaryRequest {
    @Positive(message = "좌석 ID는 음수일 수 없습니다.")
    private Long seatId;
    @Positive(message = "사용자 ID는 음수일 수 없습니다.")
    private Long userId;

    public ReservationTemporaryRequest(Long seatId, Long userId) {
        this.seatId = seatId;
        this.userId = userId;
    }
}
