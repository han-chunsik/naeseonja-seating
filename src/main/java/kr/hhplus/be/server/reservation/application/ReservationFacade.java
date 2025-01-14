package kr.hhplus.be.server.reservation.application;

import kr.hhplus.be.server.concert.domain.service.ConcertService;
import kr.hhplus.be.server.queue.domain.service.QueueTokenService;
import kr.hhplus.be.server.reservation.domain.service.ReservationService;
import kr.hhplus.be.server.reservation.interfaces.dto.request.ReservationTemporaryRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ReservationFacade {
    private final ConcertService concertService;
    private final ReservationService reservationService;
    private final QueueTokenService queueTokenService;

    private static final Logger logger = LoggerFactory.getLogger(ReservationFacade.class);

    public boolean reserveTemporary(ReservationTemporaryRequest request){
//        try {
        // To-Do 보상 트랜잭션 구현
            // 좌석 상태 비활성
//            concertService.deactivateSeat(request.getSeatId());
//            // 임시 예약 정보 등록
//            reservationService.creatReservedTemp(request.getSeatId(), request.getUserId(), request.getPrice());
//            // 대기열 토큰 만료
//            queueTokenService.expireToken(request.getUserId());

//        } catch (Exception e) {
//            // 좌석 상태 활성
//            concertService.activateSeat(request.getSeatId());
//
//            // 임시 예약 정보 삭제
//            reservationService.cancelReservedTemp(request.getSeatId(), request.getUserId());
//            return false;
//        }
        return true;
    }
}
