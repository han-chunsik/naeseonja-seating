package kr.hhplus.be.server.reservation.domain.model;

import jakarta.persistence.*;
import kr.hhplus.be.server.reservation.exception.ReservationErrorCode;
import kr.hhplus.be.server.reservation.exception.ReservationException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "seat_id", nullable = false)
    private Long seatId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "price", nullable = false)
    private Long price;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    public enum Status {
        HOLD, EXPIRED, RESERVED
    }

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "reserved_at")
    private LocalDateTime reservedAt;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

    public Reservation(Long seatId, Long userId, Long price, Status status) {
        this.seatId = seatId;
        this.userId = userId;
        this.price = price;
        this.status = status;
    }

    public void setReservationExpired() {
        if (status == Status.EXPIRED) {
            throw new ReservationException(ReservationErrorCode.INVALID_STATUS_TRANSITION, status, Status.EXPIRED);
        }
        this.status = Status.EXPIRED;
        this.expiredAt = LocalDateTime.now();
    }

    public void setReservationReserved() {
        if (status == Status.RESERVED) {
            throw new ReservationException(ReservationErrorCode.INVALID_STATUS_TRANSITION, status, Status.RESERVED);
        }
        this.status = Status.RESERVED;
        this.reservedAt = LocalDateTime.now();
    }

    public void checkAlreadyReserved(){
        if (status == Status.RESERVED) {
            throw new ReservationException(ReservationErrorCode.RESERVATION_RESERVED_CONFLICT, id);
        }
    }

    public static Reservation createTempreservation(Long seatId, Long userId, Long price) {
        return new Reservation(seatId, userId, price, Status.HOLD);
    }
}
