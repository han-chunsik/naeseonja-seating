package kr.hhplus.be.server.balance.domain;

import kr.hhplus.be.server.balance.domain.model.Balance;
import kr.hhplus.be.server.balance.domain.model.BalanceHistory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BalanceHistoryTest {
    @Nested
    @DisplayName("createBalanceHistoryTest")
    class createBalanceHistoryTest {
        @Test
        @DisplayName("Balance객체와 충전 금액을 입력하면, 타입이 CHARGE인 BalanceHistory 객체를 반환한다.")
        void 충전_객체_반환() {
            //Given
            long balanceId = 1L;
            long userId = 123L;
            long currentBalance = 100L;
            long amount = 2000L;
            Balance userBalance = new Balance(balanceId, userId, currentBalance);

            BalanceHistory history = BalanceHistory.createChargeBalanceHistory(userBalance, amount);

            // 검증
            assertNotNull(history);
            assertEquals(userBalance.getId(), history.getBalanceId());
            assertEquals(amount, history.getAmount());
            assertEquals(BalanceHistory.Type.CHARGE, history.getType());
        }
        @Test
        @DisplayName("Balance객체와 사용 금액을 입력하면, 타입이 USE인 BalanceHistory 객체를 반환한다.")
        void 사용_객체_반환() {
            //Given
            long balanceId = 1L;
            long userId = 123L;
            long currentBalance = 100L;
            long amount = 2000L;
            Balance userBalance = new Balance(balanceId, userId, currentBalance);

            BalanceHistory history = BalanceHistory.createUseBalanceHistory(userBalance, amount);

            // 검증
            assertNotNull(history);
            assertEquals(userBalance.getId(), history.getBalanceId());
            assertEquals(amount, history.getAmount());
            assertEquals(BalanceHistory.Type.USE, history.getType());
        }
    }
}
