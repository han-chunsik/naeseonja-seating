package kr.hhplus.be.server.concert.domain.repository;

import kr.hhplus.be.server.concert.domain.model.ConcertSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConcertScheduleRepository extends JpaRepository<ConcertSchedule, Long> {
    List<ConcertSchedule> findConcertSchedulesByConcertIdAndScheduleDateAfterAndStatus(Long concertId, LocalDate scheduleDate, ConcertSchedule.Status status);
    Optional<ConcertSchedule> findConcertScheduleById(Long id);
}
