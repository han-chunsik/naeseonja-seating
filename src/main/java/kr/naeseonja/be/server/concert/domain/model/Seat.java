package kr.naeseonja.be.server.concert.domain.model;

import jakarta.persistence.*;
import kr.naeseonja.be.server.concert.exception.ConcertErrorCode;
import kr.naeseonja.be.server.concert.exception.ConcertException;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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

    public void setSeatAvailable() {
        if (status == Status.AVAILABLE) {
            throw new ConcertException(ConcertErrorCode.INVALID_STATUS_TRANSITION, status, Status.AVAILABLE);
        }
        this.status = Status.AVAILABLE;
    }

    public void validateSetNotAvailableSeat() {
        if (status == Status.NOT_AVAILABLE) {
            throw new ConcertException(ConcertErrorCode.NOT_AVAILABLE_SEAT, id);
        }
        this.status = Status.NOT_AVAILABLE;
    }
}
