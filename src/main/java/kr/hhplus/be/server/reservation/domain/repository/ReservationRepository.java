package kr.hhplus.be.server.reservation.domain.repository;

import kr.hhplus.be.server.reservation.domain.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findFirstBySeatIdAndUserId(Long seatId, Long userId);
    Optional<Reservation> findFirstByIdAndUserId(Long id, Long userId);
    List<Reservation> findAllByStatusAndCreatedAtBefore(Reservation.Status status, LocalDateTime createdAt);
}
