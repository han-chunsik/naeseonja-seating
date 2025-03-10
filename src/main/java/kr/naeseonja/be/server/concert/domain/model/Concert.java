package kr.naeseonja.be.server.concert.domain.model;

import jakarta.persistence.*;
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

    public Concert(Long id, String concertName) {
        this.id = id;
        this.concertName = concertName;
    }
}
