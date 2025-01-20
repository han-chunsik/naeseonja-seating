package kr.hhplus.be.server.reservation.exception;

import lombok.Getter;

@Getter
public class ReservationException extends RuntimeException{
    private final ReservationErrorCode errorCode;

    public ReservationException(ReservationErrorCode errorCode, Object... args) {
        super(errorCode.getMessageWithArgs(args));
        this.errorCode = errorCode;
    }
}
