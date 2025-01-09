package kr.hhplus.be.server.reservation.domain.service;

import jakarta.transaction.Transactional;
import kr.hhplus.be.server.reservation.domain.entity.Reservation;
import kr.hhplus.be.server.reservation.domain.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Transactional
    public void creatReservedTemp(Long seatId, Long userId, Long price) {
        Reservation newReservation = Reservation.create(seatId, userId, price, Reservation.Status.HOLD);
        reservationRepository.save(newReservation);
    }

    @Transactional
    public void cancelReservedTemp(Long seatId, Long userId) {
        Reservation reservation = reservationRepository.findBySeatIdAndUserId(seatId, userId);
        reservationRepository.delete(reservation);
    }

}
