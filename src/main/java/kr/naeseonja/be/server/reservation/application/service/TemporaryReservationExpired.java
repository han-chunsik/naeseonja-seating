package kr.naeseonja.be.server.reservation.application.service;

import kr.naeseonja.be.server.concert.domain.service.ConcertService;
import kr.naeseonja.be.server.reservation.domain.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TemporaryReservationExpired {

    private final ReservationService reservationService;
    private final ConcertService concertService;

    public void TempReservationExpired() {
        // 임시예약 생성된지 5분 경과 시 예약 만료 및 좌석 활성화
        List<Long> seatIds = reservationService.deleteExpiredTempReservations();
        if (!seatIds.isEmpty()) {
            concertService.activateSeatList(seatIds);
        }
    }
}
