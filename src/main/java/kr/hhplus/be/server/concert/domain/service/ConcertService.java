package kr.hhplus.be.server.concert.domain.service;

import kr.hhplus.be.server.concert.domain.dto.ConcertScheduleResult;
import kr.hhplus.be.server.concert.domain.dto.ConcertSeatResult;
import kr.hhplus.be.server.concert.domain.model.ConcertSchedule;
import kr.hhplus.be.server.concert.domain.model.Seat;
import kr.hhplus.be.server.concert.domain.repository.ConcertRepository;
import kr.hhplus.be.server.concert.domain.repository.ConcertScheduleRepository;
import kr.hhplus.be.server.concert.domain.repository.ConcertSeatRepository;
import kr.hhplus.be.server.concert.exception.ConcertErrorCode;
import kr.hhplus.be.server.concert.exception.ConcertException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConcertService {

    private final ConcertScheduleRepository concertScheduleRepository;
    private final ConcertSeatRepository concertSeatRepository;
    private final ConcertRepository concertRepository;

    @Transactional(readOnly = true)
    public List<ConcertScheduleResult> getAvailableScheduleList(Long concertId) {
        concertRepository.findById(concertId)
                .orElseThrow(() -> new ConcertException(ConcertErrorCode.CONCERT_NOT_FOUND, concertId));

        LocalDate today = LocalDate.now();
        List<ConcertSchedule> availableConcertSchedules = concertScheduleRepository
                .findConcertSchedulesByConcertIdAndScheduleDateAfterAndStatus(
                concertId, today, ConcertSchedule.Status.AVAILABLE);

        return availableConcertSchedules.stream()
                .map(ConcertScheduleResult::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ConcertSeatResult> getAvailableSeatList(Long concertScheduleId) {
        ConcertSchedule concertSchedule = concertScheduleRepository
                .findConcertScheduleById(concertScheduleId)
                .orElseThrow(() -> new ConcertException(ConcertErrorCode.SCHEDULE_NOT_FOUND, concertScheduleId));
        concertSchedule.validateScheduleDate();

        List<Seat> availableSeats = concertSeatRepository.findSeatsByConcertScheduleIdAndStatus(
                concertScheduleId, Seat.Status.AVAILABLE);

        return availableSeats.stream()
                .map(ConcertSeatResult::from)
                .collect(Collectors.toList());
    }
}
