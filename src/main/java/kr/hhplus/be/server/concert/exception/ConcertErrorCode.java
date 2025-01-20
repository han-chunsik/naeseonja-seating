package kr.hhplus.be.server.concert.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ConcertErrorCode {
        INVALID_SCHEDULE_DATE(HttpStatus.BAD_REQUEST.value(), "예약 가능한 날짜는 오늘 이후여야 합니다. (요청값: %s)"),
        SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "존재하지 않는 스케줄 ID입니다. (요청값: %s)"),
        CONCERT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "존재하지 않는 콘서트 ID입니다. (요청값: %s)"),
        SEAT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "존재하지 않는 좌석 ID입니다. (요청값: %s)"),
        NOT_AVAILABLE_SEAT(HttpStatus.BAD_REQUEST.value(), "예약 불가한 좌석입니다. (요청값: %s)");


        private final int code;
        private final String message;

        public String getMessageWithArgs(Object... args) {
                return String.format(message, args);
        }
}
