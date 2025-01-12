package kr.hhplus.be.server.balance.exception;

import lombok.Getter;

@Getter
public class BalanceException extends RuntimeException {
    private final BalanceErrorCode errorCode;

    public BalanceException(BalanceErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
