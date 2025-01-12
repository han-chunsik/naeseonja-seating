package kr.hhplus.be.server.concert.interfaces.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ConcertScheduleResponse {
    private long id;
    private long concertId;
    private LocalDate scheduleDate;

    @Builder
    public ConcertScheduleResponse(long id, long concertId, LocalDate scheduleDate) {
        this.id = id;
        this.concertId = concertId;
        this.scheduleDate = scheduleDate;
    }
}
