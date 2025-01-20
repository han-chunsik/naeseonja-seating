package kr.hhplus.be.server.reservation.presentation.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservationRequest {
    @Positive(message = "예약 ID는 음수일 수 없습니다.")
    private Long reservationId;
    @Positive(message = "사용자 ID는 음수일 수 없습니다.")
    private Long userId;

    public ReservationRequest(Long reservationId, Long userId) {
        this.reservationId = reservationId;
        this.userId = userId;
    }
}
