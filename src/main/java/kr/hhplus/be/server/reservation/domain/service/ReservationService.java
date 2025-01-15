package kr.hhplus.be.server.reservation.domain.service;

import jakarta.transaction.Transactional;
import kr.hhplus.be.server.reservation.domain.model.Reservation;
import kr.hhplus.be.server.reservation.domain.repository.ReservationRepository;
import kr.hhplus.be.server.reservation.exception.ReservationErrorCode;
import kr.hhplus.be.server.reservation.exception.ReservationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Transactional
    public void creatReservedTemp(Long seatId, Long userId, Long price) {
        Reservation newReservation = Reservation.createTempreservation(seatId, userId, price);
        reservationRepository.save(newReservation);
    }

    @Transactional
    public void cancelReservedTemp(Long seatId, Long userId) {
        reservationRepository.findFirstBySeatIdAndUserId(seatId, userId).ifPresent(reservation -> {
            reservation.setResercationExpired();
            reservationRepository.save(reservation);
        });
    }

    @Transactional
    public void confirm(Long reservationId, Long userId) {
        // 예약 상태 변경
        Reservation reservation = reservationRepository.findFirstByIdAndUserId(reservationId, userId)
                .orElseThrow(() -> new ReservationException(ReservationErrorCode.RESERVATION_NOT_FOUND, reservationId));
        reservation.setResercationReserved();
        reservationRepository.save(reservation);

    }

    @Transactional
    public void holdReserved(Long reservationId, Long userId) {
        reservationRepository.findFirstByIdAndUserId(reservationId, userId).ifPresent(reservation -> {
            reservation.setResercationHold();
            reservationRepository.save(reservation);
        });
    }

    @Transactional
    public Reservation getReservationTemp(Long reservationId, Long userId) {
        Reservation reservation = reservationRepository.findFirstByIdAndUserId(reservationId, userId)
                .orElseThrow(() -> new ReservationException(ReservationErrorCode.RESERVATION_NOT_FOUND, reservationId));
        reservation.checkAlreadyReserved();
        return reservation;
    }
}
