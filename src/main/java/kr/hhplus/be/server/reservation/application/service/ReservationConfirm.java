package kr.hhplus.be.server.reservation.application.service;


import kr.hhplus.be.server.balance.domain.service.BalanceService;
import kr.hhplus.be.server.reservation.application.dto.ReservationResult;
import kr.hhplus.be.server.reservation.application.event.ReservationEventPublisher;
import kr.hhplus.be.server.reservation.domain.event.ConfirmDataSentEvent;
import kr.hhplus.be.server.reservation.domain.model.Reservation;
import kr.hhplus.be.server.reservation.domain.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationConfirm {

    private final ReservationService reservationService;
    private final BalanceService balanceService;
    private final ReservationEventPublisher reservationEventPublisher;

    public ReservationResult reserveConfirm(Long userId, Long reservationId) {
        // 임시 예약 가져오기
        Reservation r = reservationService.getReservationTemp(reservationId, userId);
        Long price = r.getPrice();

        // 잔액 사용
        balanceService.useBalance(userId, price);
        try {
            // 예약 확정
            reservationService.confirm(reservationId,userId);

            // 데이터 플랫폼 전송
            ConfirmDataSentEvent event = ConfirmDataSentEvent.fromDomain(r);
            reservationEventPublisher.confirmDataSentPublish(event);

        } catch (Exception e) {
            // 잔액 롤백
            balanceService.chargeBalance(userId, price);

            throw new RuntimeException("reservation failed: " + e.getMessage(), e);
        }
        return new ReservationResult(reservationId, userId, price);
    }
}
