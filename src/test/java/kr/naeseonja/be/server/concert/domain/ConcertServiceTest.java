package kr.naeseonja.be.server.concert.domain;

import kr.naeseonja.be.server.concert.domain.dto.ConcertScheduleResult;
import kr.naeseonja.be.server.concert.domain.dto.ConcertSeatResult;
import kr.naeseonja.be.server.concert.domain.model.Concert;
import kr.naeseonja.be.server.concert.domain.model.ConcertSchedule;
import kr.naeseonja.be.server.concert.domain.model.Seat;
import kr.naeseonja.be.server.concert.domain.repository.ConcertRepository;
import kr.naeseonja.be.server.concert.domain.repository.ConcertScheduleRepository;
import kr.naeseonja.be.server.concert.domain.repository.ConcertSeatRepository;
import kr.naeseonja.be.server.concert.domain.service.ConcertService;
import kr.naeseonja.be.server.concert.exception.ConcertErrorCode;
import kr.naeseonja.be.server.concert.exception.ConcertException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ConcertServiceTest {

    @InjectMocks
    private ConcertService concertService;

    @Mock
    private ConcertScheduleRepository concertScheduleRepository;

    @Mock
    private ConcertSeatRepository concertSeatRepository;

    @Mock
    private ConcertRepository concertRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);  // Mockito 초기화
    }

    @Nested
    @DisplayName("getAvailableScheduleListTest")
    class getAvailableScheduleListTest {
        @Test
        @DisplayName("존재하지 않은 콘서트 ID를 입력한 경우 ConcertException을 반환한다.")
        void 유효하지_않은_콘서트 () {
            // Given
            Long concertId = 1L;

            when(concertRepository.findById(concertId)).thenReturn(Optional.empty());

            // When & Then
            ConcertException exception = assertThrows(ConcertException.class, () ->
                    concertService.getAvailableScheduleList(concertId)
            );

            assertEquals(ConcertErrorCode.CONCERT_NOT_FOUND.getMessageWithArgs(concertId), exception.getMessage());
            verify(concertScheduleRepository, times(0))
                    .findConcertSchedulesByConcertIdAndScheduleDateAfterAndStatus(
                            eq(concertId), any(LocalDate.class), eq(ConcertSchedule.Status.AVAILABLE));
        }

        @Test
        @DisplayName("존재하는 콘서트 ID를 입력하면, 유효한 스케줄 있을 경우, 유효한 콘서트 목록을 반환한다.")
        void 유효한_콘서트_스케줄_있음 () {
            // Given
            Long concertId = 1L;
            LocalDateTime currentDate = LocalDateTime.now();
            Concert mockConcert = new Concert(concertId, "Concert Name", currentDate);
            List<ConcertSchedule> mockSchedules = List.of(
                    new ConcertSchedule(1L, concertId, LocalDate.now().plusDays(1), ConcertSchedule.Status.AVAILABLE),
                    new ConcertSchedule(2L, concertId, LocalDate.now().plusDays(2), ConcertSchedule.Status.AVAILABLE)
            );

            when(concertRepository.findById(concertId)).thenReturn(Optional.of(mockConcert));
            when(concertScheduleRepository.findConcertSchedulesByConcertIdAndScheduleDateAfterAndStatus(
                    eq(concertId), any(LocalDate.class), eq(ConcertSchedule.Status.AVAILABLE)))
                    .thenReturn(mockSchedules);

            // When
            List<ConcertScheduleResult> result = concertService.getAvailableScheduleList(concertId);

            // Then
            assertNotNull(result);
            assertEquals(2, result.size());
            assertEquals(mockSchedules.get(0).getId(), result.get(0).getId());
            assertEquals(mockSchedules.get(1).getId(), result.get(1).getId());

            verify(concertRepository, times(1)).findById(concertId);
            verify(concertScheduleRepository, times(1))
                    .findConcertSchedulesByConcertIdAndScheduleDateAfterAndStatus(
                            eq(concertId), any(LocalDate.class), eq(ConcertSchedule.Status.AVAILABLE));
        }

        @Test
        @DisplayName("존재하는 콘서트 ID를 입력하면, 유효한 스케줄이 없는 경우, 빈 콘서트 목록을 반환한다.")
        void 유효한_콘서트_스케줄_없음 () {
            // Given
            Long concertId = 1L;
            LocalDateTime currentDate = LocalDateTime.now();

            Concert mockConcert = new Concert(concertId, "Concert Name", currentDate);
            when(concertRepository.findById(concertId)).thenReturn(Optional.of(mockConcert));
            when(concertScheduleRepository.findConcertSchedulesByConcertIdAndScheduleDateAfterAndStatus(
                    eq(concertId), any(LocalDate.class), eq(ConcertSchedule.Status.AVAILABLE)))
                    .thenReturn(Collections.emptyList());

            // When
            List<ConcertScheduleResult> result = concertService.getAvailableScheduleList(concertId);

            // Then
            assertNotNull(result);
            assertTrue(result.isEmpty());

            verify(concertRepository, times(1)).findById(concertId);
            verify(concertScheduleRepository, times(1))
                    .findConcertSchedulesByConcertIdAndScheduleDateAfterAndStatus(
                            eq(concertId), any(LocalDate.class), eq(ConcertSchedule.Status.AVAILABLE));
        }
    }

    @Nested
    @DisplayName("getAvailableSeatListTest")
    class getAvailableSeatListTest {
        @Test
        @DisplayName("존재하지 않은 콘서트 스케줄 ID를 입력한 경우 ConcertException을 반환한다.")
        void 존재하지_않은_콘서트_스케줄 () {
            // Given
            Long concertScheduleId = 1L;

            when(concertScheduleRepository.findConcertScheduleById(concertScheduleId))
                    .thenReturn(Optional.empty());

            // When & Then
            ConcertException exception = assertThrows(ConcertException.class, () ->
                    concertService.getAvailableSeatList(concertScheduleId)
            );

            assertEquals(ConcertErrorCode.SCHEDULE_NOT_FOUND.getMessageWithArgs(concertScheduleId), exception.getMessage());
            verify(concertSeatRepository, times(0)).findSeatsByConcertScheduleIdAndStatus(concertScheduleId, Seat.Status.AVAILABLE);
        }

        @Test
        @DisplayName("유효하지 않은 콘서트 스케줄 ID를 입력한 경우 ConcertException을 반환한다.")
        void 유효하지_않은_콘서트_스케줄 () {
            // Given
            Long concertId = 1L;
            Long concertScheduleId = 1L;
            ConcertSchedule concertSchedule = new ConcertSchedule(concertScheduleId, concertId, LocalDate.now().minusDays(1), ConcertSchedule.Status.AVAILABLE);

            when(concertScheduleRepository.findConcertScheduleById(concertScheduleId))
                    .thenReturn(Optional.of(concertSchedule));

            // When & Then
            ConcertException exception = assertThrows(ConcertException.class, () ->
                    concertService.getAvailableSeatList(concertScheduleId)
            );

            assertEquals(ConcertErrorCode.INVALID_SCHEDULE_DATE.getMessageWithArgs(concertSchedule.getScheduleDate()), exception.getMessage());
            verify(concertSeatRepository, times(0)).findSeatsByConcertScheduleIdAndStatus(concertScheduleId, Seat.Status.AVAILABLE);
        }

        @Test
        @DisplayName("유효한 콘서트 스케줄 ID를 입력하면, 유효한 좌석이 있을 경우, 유효한 좌석 목록을 반환한다.")
        void 유효한_좌석_있음  () {
            // Given
            Long concertScheduleId = 1L;

            ConcertSchedule mockSchedule = mock(ConcertSchedule.class);
            List<Seat> mockSeats = List.of(
                    new Seat(1L, 1L, 1, Seat.Status.AVAILABLE, 100000L),
                    new Seat(2L, 1L, 2, Seat.Status.AVAILABLE, 100000L)
            );

            when(concertScheduleRepository.findConcertScheduleById(concertScheduleId))
                    .thenReturn(Optional.of(mockSchedule));
            when(concertSeatRepository.findSeatsByConcertScheduleIdAndStatus(concertScheduleId, Seat.Status.AVAILABLE))
                    .thenReturn(mockSeats);

            // When
            List<ConcertSeatResult> result = concertService.getAvailableSeatList(concertScheduleId);

            // Then
            assertNotNull(result);
            assertEquals(2, result.size());
            verify(mockSchedule, times(1)).validateScheduleDate();
            verify(concertSeatRepository, times(1)).findSeatsByConcertScheduleIdAndStatus(concertScheduleId, Seat.Status.AVAILABLE);
        }

        @Test
        @DisplayName("유효한 콘서트 스케줄 ID를 입력하면, 유효한 좌석이 있을 경우, 빈 좌석 목록을 반환한다.")
        void 유효한_좌석_없음  () {
            // Given
            Long concertScheduleId = 1L;

            ConcertSchedule mockSchedule = mock(ConcertSchedule.class);

            when(concertScheduleRepository.findConcertScheduleById(concertScheduleId))
                    .thenReturn(Optional.of(mockSchedule));
            when(concertSeatRepository.findSeatsByConcertScheduleIdAndStatus(concertScheduleId, Seat.Status.AVAILABLE))
                    .thenReturn(Collections.emptyList());

            // When
            List<ConcertSeatResult> result = concertService.getAvailableSeatList(concertScheduleId);

            // Then
            assertNotNull(result);
            assertTrue(result.isEmpty());
            verify(mockSchedule, times(1)).validateScheduleDate();
            verify(concertSeatRepository, times(1)).findSeatsByConcertScheduleIdAndStatus(concertScheduleId, Seat.Status.AVAILABLE);
        }
    }
}
