package kr.hhplus.be.server.app.concert.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "seat")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "concert_schedule_id", nullable = false)
    private Long concertScheduleId;

    @Column(name = "seat_number", nullable = false)
    private int seatNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    public enum Status {
        AVAILABLE, NOT_AVAILABLE
    }

    @Column(name = "price", nullable = false)
    private Long price;

    public Seat(Long id, Long concertScheduleId, int seatNumber, Status status, Long price) {
        this.id = id;
        this.concertScheduleId = concertScheduleId;
        this.seatNumber = seatNumber;
        this.status = status;
        this.price = price;
    }

    public Seat() {
    }

    public Long getId() {
        return id;
    }

    public Long getConcertScheduleId() {
        return concertScheduleId;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public Status getStatus() {
        return status;
    }

    public Long getPrice() {
        return price;
    }
}
