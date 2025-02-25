package kr.hhplus.be.server.reservation.domain.outbox;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationOutboxRepository extends JpaRepository<ReservationOutbox, Long> {
    ReservationOutbox findByPayload(String id);
    List<ReservationOutbox> findAllByStatusAndUpdatedAtBefore(ReservationOutbox.Status status, LocalDateTime updatedAt);
}
