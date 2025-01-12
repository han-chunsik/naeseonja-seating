package kr.hhplus.be.server.balance;

import kr.hhplus.be.server.balance.domain.entity.Balance;
import kr.hhplus.be.server.balance.domain.repository.BalanceRepository;
import kr.hhplus.be.server.balance.domain.service.BalanceLimit;
import kr.hhplus.be.server.balance.domain.service.BalanceService;
import kr.hhplus.be.server.balance.domain.dto.BalanceChargeResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class BalanceServiceTest {
    @InjectMocks
    private BalanceService balanceService;

    @Mock
    private BalanceRepository balanceRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);  // Mockito 초기화
    }

    @Nested
    @DisplayName("chargeBalanceTest")
    class chargeBalanceTest {
        @Test
        @DisplayName("성공: 사용자의 잔액이 존재하지 않는 경우 기존 잔액 0원으로 최종 잔액은 요청 금액")
        void 최초_충전 () {
            //  Given
            Long userId = 1L;
            Long amount = 5000L;

            when(balanceRepository.findFirstByUserIdWithLock(userId)).thenReturn(null);

            // When
            BalanceChargeResult result = balanceService.chargeBalance(userId, amount);

            //Then
            assertEquals(amount, result.getFinalBalance());
        }

        @Test
        @DisplayName("성공: 사용자의 잔액이 이미 존재하는 경우, 최종 잔액은 기존 잔액에 금액을 추가한 금액")
        void 추가_충전 () {
            // Given
            Long userId = 1L;
            Long currentBalance = 3000L;
            Long amount = 5000L;

            Balance existingBalance = new Balance();
            existingBalance.create(userId, currentBalance);

            when(balanceRepository.findFirstByUserIdWithLock(userId)).thenReturn(existingBalance);

            // When
            BalanceChargeResult result = balanceService.chargeBalance(userId, amount);

            //Then
            assertEquals(amount + currentBalance, result.getFinalBalance());
        }

        @Test
        @DisplayName("실패: 최소 금액 이하로 충전 요청할 경우 IllegalArgumentException 에러 발생 및 잔액 업데이트 미 호출")
        void 최소_충전 () {
            // Given
            Long userId = 1L;
            Long invalidAmount = 5L;

            // When & Then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                    balanceService.chargeBalance(userId, invalidAmount)
            );

            assertEquals("최소 충전 잔액은 " + BalanceLimit.BALANCE_RECHARGE_LIMIT_MIN.getLimit() + "입니다.", exception.getMessage());
            verify(balanceRepository, times(0)).save(any());
        }

        @Test
        @DisplayName("실패: 충전 후 잔액이 최대 잔액을 넘을 경우 IllegalArgumentException 에러 발생 및 잔액 업데이트 미 호출")
        void 초과_충전 () {
            // Given
            Long userId = 1L;
            Long currentBalance = 9999999L;
            Long amount = 5000L;

            Balance existingBalance = new Balance();
            existingBalance.create(userId, currentBalance);
            when(balanceRepository.findFirstByUserIdWithLock(userId)).thenReturn(existingBalance);

            // When & Then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                    balanceService.chargeBalance(userId, amount)
            );

            assertEquals("충전 후 잔액이 " + BalanceLimit.BALANCE_LIMIT_MAX.getLimit() + "를 초과할 수 없습니다.", exception.getMessage());
            verify(balanceRepository, times(0)).save(any());
        }
    }
}
