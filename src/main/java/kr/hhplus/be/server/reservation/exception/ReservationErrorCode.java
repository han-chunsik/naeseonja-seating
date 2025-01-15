package kr.hhplus.be.server.reservation.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReservationErrorCode {
    RESERVATION_RESERVED_CONFLICT(HttpStatus.CONFLICT.value(), "이미 예약 되어있습니다. (요청값: %s)"),
    RESERVATION_TEMP_CONFLICT(HttpStatus.CONFLICT.value(), "이미 임시예약 되어있습니다. (요청값: %s)"),
    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "예약 내역이 존재하지 않습니다. (요청값: %s)");
    private final int code;
    private final String message;

    public String getMessageWithArgs(Object... args) {
        return String.format(message, args);
    }
}
