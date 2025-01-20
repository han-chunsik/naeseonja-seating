package kr.hhplus.be.server.concert.domain;

import kr.hhplus.be.server.concert.domain.model.Seat;
import kr.hhplus.be.server.concert.exception.ConcertErrorCode;
import kr.hhplus.be.server.concert.exception.ConcertException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SeatTest {
    @Nested
    @DisplayName("validateAvailableSeatTest")
    class validateAvailableSeatTest {
        @Test
        @DisplayName("좌석이 활성 상태가 아닐 경우 ConcertException을 반환한다.")
        void 활성_상태_체크() {
            //  Given
            Seat seat = new Seat(1L, 1L, 1, Seat.Status.NOT_AVAILABLE, 1000L);

            // When&Then
            ConcertException exception = assertThrows(ConcertException.class, seat::validateSetNotAvailableSeat);
            assertEquals(ConcertErrorCode.NOT_AVAILABLE_SEAT.getMessageWithArgs(seat.getId()), exception.getMessage());
        }
    }
}
