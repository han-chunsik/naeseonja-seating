package kr.hhplus.be.server.app.concert.domain.entity;

import jakarta.persistence.*;

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

    public Concert() {
    }

    public Long getId() {
        return id;
    }

    public String getConcertName() {
        return concertName;
    }
}
