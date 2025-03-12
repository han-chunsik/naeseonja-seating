package kr.naeseonja.be.server.concert.presentation.dto.response;

import kr.naeseonja.be.server.concert.domain.dto.ConcertResult;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConcertResponse {
    private Long id;
    private String concertName;

    public ConcertResponse(Long id, String concertName) {
        this.id = id;
        this.concertName = concertName;
    }

    public static ConcertResponse from(ConcertResult concertResult) {
        return new ConcertResponse(concertResult.getId(), concertResult.getConcertName());
    }
}
