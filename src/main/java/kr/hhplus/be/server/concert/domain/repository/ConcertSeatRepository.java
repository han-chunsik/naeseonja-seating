package kr.hhplus.be.server.concert.domain.repository;

import kr.hhplus.be.server.concert.domain.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConcertSeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findSeatsByConcertScheduleIdAndStatus(Long concertScheduleId, Seat.Status status);
}
