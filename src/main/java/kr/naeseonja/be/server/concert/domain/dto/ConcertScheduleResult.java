package kr.naeseonja.be.server.concert.domain.dto;

import kr.naeseonja.be.server.concert.domain.model.ConcertSchedule;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ConcertScheduleResult {
    private long id;
    private long concertId;
    private LocalDate scheduleDate;

    public ConcertScheduleResult(long id, long concertId, LocalDate scheduleDate) {
        this.id = id;
        this.concertId = concertId;
        this.scheduleDate = scheduleDate;
    }

    public static ConcertScheduleResult from(ConcertSchedule concertSchedule) {
        return new ConcertScheduleResult(concertSchedule.getId(), concertSchedule.getConcertId(), concertSchedule.getScheduleDate());
    }
}
