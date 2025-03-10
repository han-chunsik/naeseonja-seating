package kr.naeseonja.be.server.reservation.domain.outbox;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Entity
@Table(name = "outbox")
public class ReservationOutbox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "event_type")
    private String eventType;

    @Column(name = "payload")
    private String payload;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    public enum Status {
        INIT, PUBLISHED, FAILED
    }

    @Column(name = "retry_cnt")
    private int retryCount;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public ReservationOutbox(String eventType, String payload, Status status) {
        this.eventType = eventType;
        this.payload = payload;
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

    public void setRetryCount() {
        this.retryCount+=1;
    }

    public void setStatusPublished() {
        this.status = Status.PUBLISHED;
        this.updatedAt = LocalDateTime.now();
    }

    public void setStatusFailed() {
        this.status = Status.FAILED;
        this.updatedAt = LocalDateTime.now();
    }

    public void setDelaySeconds(int retryCount){
        int delaySeconds = (int) Math.pow(2, retryCount);
        this.updatedAt = LocalDateTime.now().plusSeconds(delaySeconds);
    }
}
