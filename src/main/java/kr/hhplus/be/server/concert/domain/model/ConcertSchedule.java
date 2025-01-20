package kr.hhplus.be.server.concert.domain.model;

import jakarta.persistence.*;
import kr.hhplus.be.server.concert.exception.ConcertErrorCode;
import kr.hhplus.be.server.concert.exception.ConcertException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "concert_schedule")
public class ConcertSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "concert_id", nullable = false)
    private Long concertId;

    @Column(name = "schedule_date", nullable = false)
    private LocalDate scheduleDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    public enum Status {
        SCHEDULED, AVAILABLE, SOLDOUT
    }

    public ConcertSchedule(Long id, Long concertId, LocalDate scheduleDate, Status status) {
        this.id = id;
        this.concertId = concertId;
        this.scheduleDate = scheduleDate;
        this.status = status;
    }

    public void validateScheduleDate() {
        LocalDate today = LocalDate.now();
        if (scheduleDate.isBefore(today)) {
            throw new ConcertException(ConcertErrorCode.INVALID_SCHEDULE_DATE, scheduleDate.toString());
        }
    }
}
