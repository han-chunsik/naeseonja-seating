package kr.hhplus.be.server.reservation.domain.service;

import jakarta.transaction.Transactional;
import kr.hhplus.be.server.reservation.domain.model.Reservation;
import kr.hhplus.be.server.reservation.domain.repository.ReservationRepository;
import kr.hhplus.be.server.reservation.exception.ReservationErrorCode;
import kr.hhplus.be.server.reservation.exception.ReservationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
            reservation.setReservationExpired();
            reservationRepository.save(reservation);
        });
    }

    @Transactional
    public void confirm(Long reservationId, Long userId) {
        Reservation reservation = reservationRepository.findFirstByIdAndUserId(reservationId, userId)
                .orElseThrow(() -> new ReservationException(ReservationErrorCode.RESERVATION_NOT_FOUND, reservationId));
        reservation.setReservationReserved();
        reservationRepository.save(reservation);

    }

    @Transactional
    public Reservation getReservationTemp(Long reservationId, Long userId) {
        Reservation reservation = reservationRepository.findFirstByIdAndUserId(reservationId, userId)
                .orElseThrow(() -> new ReservationException(ReservationErrorCode.RESERVATION_NOT_FOUND, reservationId));
        reservation.checkAlreadyReserved();
        return reservation;
    }

    @Transactional
    public List<Long> deleteExpiredTempReservations() {
        LocalDateTime fiveMinutesAgo = LocalDateTime.now().minusMinutes(5);

        List<Reservation> expiredTempReservations = reservationRepository
                .findAllByStatusAndCreatedAtBefore(Reservation.Status.HOLD, fiveMinutesAgo);

        if (!expiredTempReservations.isEmpty()) {
            expiredTempReservations.forEach(Reservation::setReservationExpired);
            reservationRepository.saveAll(expiredTempReservations);

            return expiredTempReservations.stream()
                    .map(Reservation::getSeatId)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
