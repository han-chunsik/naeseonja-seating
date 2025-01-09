package kr.hhplus.be.server.balance;

import kr.hhplus.be.server.balance.domain.entity.Balance;
import kr.hhplus.be.server.balance.domain.repository.BalanceRepository;
import kr.hhplus.be.server.balance.domain.service.BalanceChargeValidatorService;
import kr.hhplus.be.server.balance.domain.service.BalanceHistoryService;
import kr.hhplus.be.server.balance.domain.service.BalanceServiceImpl;
import kr.hhplus.be.server.balance.domain.vo.BalanceChargeVO;
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
    private BalanceServiceImpl balanceService;

    @Mock
    private BalanceRepository balanceRepository;

    @Mock
    private BalanceChargeValidatorService balanceChargeValidatorService;

    @Mock
    private BalanceHistoryService balanceHistoryService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);  // Mockito 초기화
    }

    @Nested
    @DisplayName("chargeBalanceTest")
    class chargeBalanceTest {
        @Test
        @DisplayName("사용자의 잔액이 존재하지 않는 경우 기존 잔액 0원으로 최종 잔액은 요청 금액")
        void 최초_충전 () {
            // Given
            Long userId = 1L;
            Long amount = 100L;

            when(balanceRepository.findFirstByUserIdWithLock(userId)).thenReturn(null);
            doNothing().when(balanceChargeValidatorService).validateChargeAmount(amount);
            doNothing().when(balanceHistoryService).saveBalanceChargeHistory(userId, amount);

            // When
            BalanceChargeVO result = balanceService.chargeBalance(userId, amount);

            //Then
            assertEquals(Long.valueOf(100L), result.getFinalBalance());
        }

        @Test
        @DisplayName("사용자의 잔액이 이미 존재하는 경우, 최종 잔액은 기존 잔액에 금액을 추가한 금액")
        void 추가_충전 () {
            // Given
            Long userId = 1L;
            Long amount = 100L;
            Long currentBalance = 1000L;

            Balance existingBalance = new Balance();
            existingBalance.create(userId, currentBalance);
            when(balanceRepository.findFirstByUserIdWithLock(userId)).thenReturn(existingBalance);

            doNothing().when(balanceChargeValidatorService).validateChargeAmount(amount);
            doNothing().when(balanceHistoryService).saveBalanceChargeHistory(userId, amount);

            // When
            BalanceChargeVO result = balanceService.chargeBalance(userId, amount);

            //Then
            assertEquals(Long.valueOf(amount + currentBalance), result.getFinalBalance());
        }

        @Test
        @DisplayName("최소 금액 이하로 충전 요청할 경우 잔액 업데이트 미 호출")
        void 최소_충전 () {
            // Given
            Long userId = 1L;

            doThrow(new IllegalArgumentException())
                    .when(balanceChargeValidatorService).validateChargeAmount(anyLong());

            // When & Then
            assertThrows(IllegalArgumentException.class, () ->
                    balanceService.chargeBalance(userId, anyLong())
            );

            verify(balanceRepository, times(0)).save(any());

        }

        @Test
        @DisplayName("충전 후 잔액이 최대 잔액을 넘을 경우 잔액 업데이트 미 호출")
        void 초과_충전 () {
            // Given
            Long userId = 1L;
            Long currentBalance = 1000L;

            Balance existingBalance = new Balance();
            existingBalance.create(userId, currentBalance);
            when(balanceRepository.findFirstByUserIdWithLock(userId)).thenReturn(existingBalance);

            doNothing().when(balanceChargeValidatorService).validateChargeAmount(anyLong());

            doThrow(new IllegalArgumentException())
                    .when(balanceChargeValidatorService).validateMaxBalance(anyLong());

            // When & Then
            assertThrows(IllegalArgumentException.class, () -> {
                balanceService.chargeBalance(userId, anyLong());
            });

            verify(balanceRepository, times(0)).save(any());
        }
    }
}
