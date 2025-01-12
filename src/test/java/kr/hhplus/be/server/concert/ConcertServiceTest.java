package kr.hhplus.be.server.concert;

import kr.hhplus.be.server.balance.domain.dto.BalanceChargeResult;
import kr.hhplus.be.server.concert.domain.dto.ConcertScheduleResult;
import kr.hhplus.be.server.concert.domain.dto.ConcertSeatResult;
import kr.hhplus.be.server.concert.domain.entity.ConcertSchedule;
import kr.hhplus.be.server.concert.domain.entity.Seat;
import kr.hhplus.be.server.concert.domain.repository.ConcertScheduleRepository;
import kr.hhplus.be.server.concert.domain.repository.ConcertSeatRepository;
import kr.hhplus.be.server.concert.domain.service.ConcertService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class ConcertServiceTest {

    @InjectMocks
    private ConcertService concertService;

    @Mock
    private ConcertScheduleRepository concertScheduleRepository;

    @Mock
    private ConcertSeatRepository concertSeatRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);  // Mockito 초기화
    }

    @Nested
    @DisplayName("getAvailableScheduleListTest")
    class getAvailableScheduleListTest {
        @Test
        @DisplayName("성공: 예약 가능 콘서트 스케줄 조회")
        void 예약_가능_콘서트_스케줄 () {
            // Given
            Long concertId = 1L;
            ConcertSchedule concertSchedule1 = new ConcertSchedule(1L, concertId, LocalDate.now().plusDays(1), ConcertSchedule.Status.AVAILABLE);
            ConcertSchedule concertSchedule2 = new ConcertSchedule(2L, concertId, LocalDate.now().plusDays(2), ConcertSchedule.Status.AVAILABLE);
            List<ConcertSchedule> availableSchedules = Arrays.asList(concertSchedule1, concertSchedule2);

            when(concertScheduleRepository.findConcertSchedulesByConcertIdAndScheduleDateAfterAndStatus(concertId, LocalDate.now(), ConcertSchedule.Status.AVAILABLE))
                    .thenReturn(availableSchedules);

            // When
            List<ConcertScheduleResult> result = concertService.getAvailableScheduleList(concertId);

            // Then
            assertEquals(2, result.size());
            assertEquals(concertSchedule1.getId(), result.get(0).getId());
            assertEquals(concertSchedule2.getId(), result.get(1).getId());
        }
    }

    @Nested
    @DisplayName("getAvailableSeatListTest")
    class getAvailableSeatListTest {
        @Test
        @DisplayName("성공: 특정 콘서트 스케줄 예약 가능 좌석 조회")
        void 예약_가능_좌석 () {
            // Given
            Long concertScheduleId = 1L;
            ConcertSchedule concertSchedule = new ConcertSchedule(1L, 1L, LocalDate.now().plusDays(1), ConcertSchedule.Status.AVAILABLE);
            Seat seat1 = new Seat(1L, concertScheduleId, 1, Seat.Status.AVAILABLE, 200000L);
            Seat seat2 = new Seat(2L, concertScheduleId, 1, Seat.Status.AVAILABLE, 200000L);
            List<Seat> availableSeats = Arrays.asList(seat1, seat2);

            when(concertSeatRepository.findSeatsByConcertScheduleIdAndStatus(concertScheduleId, Seat.Status.AVAILABLE))
                    .thenReturn(availableSeats);
            when(concertScheduleRepository.findConcertScheduleById(concertScheduleId))
                    .thenReturn(concertSchedule);

            // When
            List<ConcertSeatResult> result = concertService.getAvailableSeatList(concertScheduleId);

            // Then
            assertEquals(2, result.size());
            assertEquals(seat1.getSeatNumber(), result.get(0).getSeatNumber());
            assertEquals(seat2.getSeatNumber(), result.get(1).getSeatNumber());
        }

        @Test
        @DisplayName("실패: 과거 날짜의 스케줄 ID로 좌석 조회 시 IllegalArgumentException 에러 발생")
        void 과거_날짜_조회 () {
            // Given
            Long concertScheduleId = 1L;
            ConcertSchedule concertSchedule = new ConcertSchedule(1L, 1L, LocalDate.now().minusDays(1), ConcertSchedule.Status.AVAILABLE);

            when(concertScheduleRepository.findConcertScheduleById(concertScheduleId))
                    .thenReturn(concertSchedule);

            // when & then
            IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
                concertService.getAvailableSeatList(concertScheduleId);
            });
            assertEquals("예약 가능한 날짜는 오늘 이후여야 합니다.", thrown.getMessage());
        }
    }
}
