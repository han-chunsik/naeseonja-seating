package kr.naeseonja.be.server.concert.presentation.dto.response;

import kr.naeseonja.be.server.concert.domain.dto.ConcertScheduleResult;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ConcertScheduleResponse {
    private long id;
    private long concertId;
    private LocalDate scheduleDate;

    public ConcertScheduleResponse(long id, long concertId, LocalDate scheduleDate) {
        this.id = id;
        this.concertId = concertId;
        this.scheduleDate = scheduleDate;
    }

    public static ConcertScheduleResponse from(ConcertScheduleResult concertScheduleResult) {
        return new ConcertScheduleResponse(concertScheduleResult.getId(), concertScheduleResult.getConcertId(), concertScheduleResult.getScheduleDate());
    }
}
