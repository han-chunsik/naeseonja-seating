package kr.hhplus.be.server.concert.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConcertSeatResult {
    private Long concertScheduleId;
    private int seatNumber;
    private Long price;

    @Builder
    public ConcertSeatResult(Long concertScheduleId, int seatNumber, Long price) {
        this.concertScheduleId = concertScheduleId;
        this.seatNumber = seatNumber;
        this.price = price;
    }
}
