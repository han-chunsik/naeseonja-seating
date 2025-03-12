package kr.naeseonja.be.server.concert.domain.dto;

import kr.naeseonja.be.server.concert.domain.model.Concert;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConcertResult {
    private Long id;
    private String concertName;

    public ConcertResult(Long id, String concertName) {
        this.id = id;
        this.concertName = concertName;
    }
    public static ConcertResult from(Concert concert) {
        return new ConcertResult(concert.getId(), concert.getConcertName());
    }
}
