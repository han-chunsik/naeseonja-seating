package kr.naeseonja.be.server.balance.exception;

import lombok.Getter;


@Getter
public class BalanceException extends RuntimeException {
    private final BalanceErrorCode errorCode;

    public BalanceException(BalanceErrorCode errorCode, Object... args) {
        super(errorCode.getMessageWithArgs(args));
        this.errorCode = errorCode;
    }
}
