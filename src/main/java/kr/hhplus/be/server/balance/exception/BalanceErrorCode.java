package kr.hhplus.be.server.balance.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BalanceErrorCode {
        BALANCE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "잔액이 없습니다."),
        INVALID_AMOUNT(HttpStatus.BAD_REQUEST.value(), "충전 금액이 최소 충전 금액보다 적습니다."),
        EXCEEDS_MAX_BALANCE(HttpStatus.BAD_REQUEST.value(), "충전 금액이 최대 잔액 한도를 초과합니다."),
        INVALID_USER_ID(HttpStatus.BAD_REQUEST.value(), "사용자 ID는 음수일 수 없습니다."),
        INVALID_CHARGE_AMOUNT(HttpStatus.BAD_REQUEST.value(), "충전 금액은 음수일 수 없습니다."),;

        private final int code;
        private final String message;
}
