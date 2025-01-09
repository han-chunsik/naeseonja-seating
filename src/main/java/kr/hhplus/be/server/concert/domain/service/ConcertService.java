package kr.hhplus.be.server.concert.domain.service;

import kr.hhplus.be.server.concert.domain.dto.ConcertScheduleResult;
import kr.hhplus.be.server.concert.domain.dto.ConcertSeatResult;
import kr.hhplus.be.server.concert.domain.entity.ConcertSchedule;
import kr.hhplus.be.server.concert.domain.entity.Seat;
import kr.hhplus.be.server.concert.domain.repository.ConcertScheduleRepository;
import kr.hhplus.be.server.concert.domain.repository.ConcertSeatRepository;
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

    @Transactional(readOnly = true)
    public List<ConcertScheduleResult> getAvailableScheduleList(Long concertId) {
        LocalDate today = LocalDate.now();
        List<ConcertSchedule> availableConcertSchedules = concertScheduleRepository.findConcertSchedulesByConcertIdAndScheduleDateAfterAndStatus(concertId, today, ConcertSchedule.Status.AVAILABLE);

        return availableConcertSchedules.stream()
                .map(concertSchedule -> ConcertScheduleResult.builder()
                        .id(concertSchedule.getId())
                        .concertId(concertSchedule.getConcertId())
                        .scheduleDate(concertSchedule.getScheduleDate())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ConcertSeatResult> getAvailableSeatList(Long concertScheduleId) {
        LocalDate today = LocalDate.now();

        ConcertSchedule concertSchedule = concertScheduleRepository.findConcertScheduleById(concertScheduleId);
        LocalDate scheduleDate = concertSchedule.getScheduleDate();

        if (scheduleDate.isBefore(today)) {
            throw new IllegalArgumentException("예약 가능한 날짜는 오늘 이후여야 합니다.");
        }

        List<Seat> availableSeats = concertSeatRepository.findSeatsByConcertScheduleIdAndStatus(concertScheduleId, Seat.Status.AVAILABLE);

        return availableSeats.stream()
                .map(concertSeat -> ConcertSeatResult.builder()
                        .concertScheduleId(concertSeat.getConcertScheduleId())
                        .seatNumber(concertSeat.getSeatNumber())
                        .price(concertSeat.getPrice())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public void activateSeat(Long seatId) {
        Seat seat = concertSeatRepository.findSeatById(seatId);
        if (seat.getStatus() == Seat.Status.AVAILABLE) {
            throw new RuntimeException("이미 활성화 되어있는 좌석 입니다.");
        }
        seat.changeStatus(Seat.Status.AVAILABLE);
        concertSeatRepository.save(seat);
    }

    @Transactional
    public void deactivateSeat(Long seatId) {
        Seat seat = concertSeatRepository.findSeatById(seatId);
        if (seat.getStatus() == Seat.Status.NOT_AVAILABLE) {
            throw new RuntimeException("이미 비활성화 되어있는 좌석 입니다.");
        }
        seat.changeStatus(Seat.Status.NOT_AVAILABLE);
        concertSeatRepository.save(seat);
    }
}
