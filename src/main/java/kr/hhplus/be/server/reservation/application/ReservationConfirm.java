package kr.hhplus.be.server.reservation.application;


import kr.hhplus.be.server.balance.domain.service.BalanceService;
import kr.hhplus.be.server.reservation.application.dto.ReservationResult;
import kr.hhplus.be.server.reservation.domain.model.Reservation;
import kr.hhplus.be.server.reservation.domain.service.ReservationService;
import kr.hhplus.be.server.reservation.event.ReservationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationConfirm {

    private final ReservationService reservationService;
    private final BalanceService balanceService;
    private final ApplicationEventPublisher eventPublisher;

    public ReservationResult reserveConfirm(Long userId, Long reservationId) {
        // 임시 예약 가져오기
        Reservation r = reservationService.getReservationTemp(reservationId, userId);
        Long price = r.getPrice();

        // 잔액 사용
        balanceService.useBalance(userId, price);
        try {
            // 예약 확정
            reservationService.confirm(reservationId,userId);

            eventPublisher.publishEvent(ReservationEvent.from(r));

        } catch (Exception e) {
            // 잔액 롤백
            balanceService.chargeBalance(userId, price);

            throw new RuntimeException("reservation failed: " + e.getMessage(), e);
        }
        return new ReservationResult(reservationId, userId, price);
    }
}
