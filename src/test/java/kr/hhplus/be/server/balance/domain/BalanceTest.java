package kr.hhplus.be.server.balance.domain;

import kr.hhplus.be.server.balance.domain.model.Balance;
import kr.hhplus.be.server.balance.exception.BalanceErrorCode;
import kr.hhplus.be.server.balance.exception.BalanceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BalanceTest {
    @Nested
    @DisplayName("chargeTest")
    class chargeTest {
        @Test
        @DisplayName("최소 충전 금액이상 입력하면, 충전 완료 금액이 최대 잔액을 넘지 않는 경우. 잔액을 더한다.")
        void 정상_충전() {
            //  Given
            long balanceId = 1L;
            long userId = 123L;
            long currentBalance = 100L;
            long amount = 10000L;

            Balance balance = new Balance(balanceId, userId, currentBalance);

            // When
            balance.charge(amount);

            // Then
            assertEquals(currentBalance + amount, balance.getBalance());
        }

        @Test
        @DisplayName("최소 충전 금액 이하 입력하면, BalanceException 오류를 반환한다.")
        void 최소_충전_금액_이하() {
            //  Given
            long balanceId = 1L;
            long userId = 123L;
            long currentBalance = 100L;
            long invalidAmount = Balance.BalanceLimit.MIN.getValue() - 10L;

            Balance balance = new Balance(balanceId, userId, currentBalance);

            // When&Then
            BalanceException exception = assertThrows(BalanceException.class, () ->
                    balance.charge(invalidAmount)
            );

            assertEquals(BalanceErrorCode.INVALID_AMOUNT.getMessageWithArgs(invalidAmount), exception.getMessage());
        }

        @Test
        @DisplayName("최소 충전 금액 이상 입력하면, 충전 완료 금액이 최대 잔액을 넘는 경우 BalanceException 오류를 반환한다.")
        void 최대_잔액_초과() {
            //  Given
            long balanceId = 1L;
            long userId = 123L;
            long invalidCurrentBalance = Balance.BalanceLimit.MAX.getValue() - 1000L;
            long invalidAmount = 1100L;

            Balance balance = new Balance(balanceId, userId, invalidCurrentBalance);

            // When&Then
            BalanceException exception = assertThrows(BalanceException.class, () ->
                    balance.charge(invalidAmount)
            );

            assertEquals(BalanceErrorCode.EXCEEDS_MAX_BALANCE.getMessageWithArgs(invalidCurrentBalance+invalidAmount), exception.getMessage());
        }
    }

    @Nested
    @DisplayName("useTest")
    class useTest {
        @Test
        @DisplayName("보유 잔액보다 적은 금액을 입력하면, 잔액을 차감한다.")
        void 정상_사용() {
            //  Given
            long balanceId = 1L;
            long userId = 123L;
            long currentBalance = 80000L;
            long amount = 50000L;

            Balance balance = new Balance(balanceId, userId, currentBalance);

            // When
            balance.use(amount);

            // Then
            assertEquals(currentBalance - amount, balance.getBalance());
        }

        @Test
        @DisplayName("보유 잔액 이상 금액을 입력하면, BalanceException 오류를 반환한다.")
        void 보유_잔액_초과_사용() {
            //  Given
            long balanceId = 1L;
            long userId = 123L;
            long currentBalance = 10000L;
            long invalidAmount = 20000;

            Balance balance = new Balance(balanceId, userId, currentBalance);

            // When&Then
            BalanceException exception = assertThrows(BalanceException.class, () ->
                    balance.use(invalidAmount)
            );

            assertEquals(BalanceErrorCode.INSUFFICIENT_BALANCE.getMessageWithArgs(invalidAmount), exception.getMessage());
        }
    }
}
