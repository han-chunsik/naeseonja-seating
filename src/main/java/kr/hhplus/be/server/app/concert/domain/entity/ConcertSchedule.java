package kr.hhplus.be.server.app.concert.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

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

    public ConcertSchedule() {
    }

    public Long getId() {
        return id;
    }

    public Long getConcertId() {
        return concertId;
    }

    public LocalDate getScheduleDate() {
        return scheduleDate;
    }

    public Status getStatus() {
        return status;
    }
}
