package kr.hhplus.be.server.concert.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Builder
    public Concert(Long id, String concertName) {
        this.id = id;
        this.concertName = concertName;
    }
}
