package kr.naeseonja.be.server.reservation.application.service;

import kr.naeseonja.be.server.concert.domain.service.ConcertService;
import kr.naeseonja.be.server.queue.domain.service.QueueTokenService;
import kr.naeseonja.be.server.reservation.application.dto.ReservationTemporaryResult;
import kr.naeseonja.be.server.reservation.domain.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ReservationTemporary {
    private final ConcertService concertService;
    private final ReservationService reservationService;
    private final QueueTokenService queueTokenService;

    public ReservationTemporaryResult reserveTemporary(Long seatId, Long userId) {
        Long price = concertService.getSeatPrice(seatId);
        boolean isSuccess = false;

        //좌석 상태 비활성
        concertService.deactivateSeat(seatId);

        Long newReservationId;
        try {
            // 임시 예약 정보 등록
            newReservationId = reservationService.creatReservedTemp(seatId, userId, price);
            isSuccess = true;
        } catch (Exception e) {
            // 임시 예약 정보 만료
            reservationService.cancelReservedTemp(seatId, userId);
            // 좌석 상태 활성
            concertService.activateSeat(seatId);

            throw new RuntimeException("Temporary reservation failed: " + e.getMessage(), e);
        } finally {
            if (isSuccess) {
                //토큰 만료
                queueTokenService.expireToken(userId);
            }
        }
        return new ReservationTemporaryResult(newReservationId, seatId, userId, price);
    }
}
