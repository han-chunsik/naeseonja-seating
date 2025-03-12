package kr.naeseonja.be.server.concert.presentation.dto.response;

import kr.naeseonja.be.server.concert.domain.dto.ConcertSeatResult;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConcertSeatResponse {
    private Long id;
    private Long concertScheduleId;
    private int seatNumber;
    private Long price;

    public ConcertSeatResponse(Long id, Long concertScheduleId, int seatNumber, Long price) {
        this.id = id;
        this.concertScheduleId = concertScheduleId;
        this.seatNumber = seatNumber;
        this.price = price;
    }

    public static ConcertSeatResponse from(ConcertSeatResult concertSeatResult) {
        return new ConcertSeatResponse(concertSeatResult.getId(), concertSeatResult.getConcertScheduleId(), concertSeatResult.getSeatNumber(), concertSeatResult.getPrice());
    }
}
