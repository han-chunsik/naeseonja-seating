package kr.hhplus.be.server.balance.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BalanceErrorCode {
        BALANCE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "해당 사용자의 잔액 데이터가 존재하지 않습니다. (요청값: %s)"),
        INVALID_AMOUNT(HttpStatus.BAD_REQUEST.value(), "충전 금액이 최소 충전 금액보다 적습니다. (요청값: %s)"),
        EXCEEDS_MAX_BALANCE(HttpStatus.BAD_REQUEST.value(), "충전 금액이 최대 잔액 한도를 초과합니다. (요청값: %s)"),
        INSUFFICIENT_BALANCE(HttpStatus.BAD_REQUEST.value(), "잔액이 충분하지 않습니다. (요청값: %s)");

        private final int code;
        private final String message;

        public String getMessageWithArgs(Object... args) {
                return String.format(message, args);
        }
}
