package kr.naeseonja.be.server.concert.domain.dto;

import kr.naeseonja.be.server.concert.domain.model.Seat;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConcertSeatResult {
    private Long concertScheduleId;
    private int seatNumber;
    private Long price;

    public ConcertSeatResult(Long concertScheduleId, int seatNumber, Long price) {
        this.concertScheduleId = concertScheduleId;
        this.seatNumber = seatNumber;
        this.price = price;
    }

    public static ConcertSeatResult from(Seat seat) {
        return new ConcertSeatResult(seat.getConcertScheduleId(), seat.getSeatNumber(), seat.getPrice());
    }
}
