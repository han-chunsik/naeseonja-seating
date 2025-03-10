package kr.naeseonja.be.server.concert.exception;

import lombok.Getter;

@Getter
public class ConcertException extends RuntimeException {
    private final ConcertErrorCode errorCode;

    public ConcertException(ConcertErrorCode errorCode, Object... args) {
        super(errorCode.getMessageWithArgs(args));
        this.errorCode = errorCode;
    }
}
