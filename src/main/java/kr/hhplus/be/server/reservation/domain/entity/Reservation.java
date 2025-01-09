package kr.hhplus.be.server.reservation.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
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

    @Column(name = "reserved_at", nullable = false)
    private LocalDateTime reservedAt;

    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiredAt;

    @Builder
    public Reservation(Long id, Long seatId, Long userId, Long price, Status status, LocalDateTime createdAt, LocalDateTime reservedAt, LocalDateTime expiredAt) {
        this.id = id;
        this.seatId = seatId;
        this.userId = userId;
        this.price = price;
        this.status = status;
        this.createdAt = createdAt;
        this.reservedAt = reservedAt;
        this.expiredAt = expiredAt;
    }
}
