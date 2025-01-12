package kr.hhplus.be.server.concert.interfaces.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConcertSeatResponse {
    private Long concertScheduleId;
    private int seatNumber;
    private Long price;

    @Builder
    public ConcertSeatResponse(Long concertScheduleId, int seatNumber, Long price) {
        this.concertScheduleId = concertScheduleId;
        this.seatNumber = seatNumber;
        this.price = price;
    }
}
