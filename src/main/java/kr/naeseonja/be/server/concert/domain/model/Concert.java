package kr.naeseonja.be.server.concert.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "concert")
public class Concert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "concert_name", nullable = false)
    private String concertName;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Concert(Long id, String concertName, LocalDateTime createdAt) {
        this.id = id;
        this.concertName = concertName;
        this.createdAt = createdAt;
    }

    public Concert(String concertName) {
        this.concertName = concertName;
        this.createdAt = LocalDateTime.now();
    }
}
