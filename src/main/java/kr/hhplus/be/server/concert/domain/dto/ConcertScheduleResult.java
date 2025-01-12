package kr.hhplus.be.server.concert.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ConcertScheduleResult {
    private long id;
    private long concertId;
    private LocalDate scheduleDate;

    @Builder
    public ConcertScheduleResult(long id, long concertId, LocalDate scheduleDate) {
        this.id = id;
        this.concertId = concertId;
        this.scheduleDate = scheduleDate;
    }
}
