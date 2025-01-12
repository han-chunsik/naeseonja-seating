package kr.hhplus.be.server.balance.domain;

import kr.hhplus.be.server.balance.domain.dto.BalanceChargeResult;
import kr.hhplus.be.server.balance.domain.dto.BalanceResult;
import kr.hhplus.be.server.balance.domain.model.Balance;
import kr.hhplus.be.server.balance.domain.repository.BalanceHistoryRepository;
import kr.hhplus.be.server.balance.domain.repository.BalanceRepository;
import kr.hhplus.be.server.balance.domain.service.BalanceService;
import kr.hhplus.be.server.balance.exception.BalanceErrorCode;
import kr.hhplus.be.server.balance.exception.BalanceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BalanceServiceTest {
    @InjectMocks
    private BalanceService balanceService;

    @Mock
    private BalanceRepository balanceRepository;

    @Mock
    private BalanceHistoryRepository balanceHistoryRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);  // Mockito 초기화
    }

    @Nested
    @DisplayName("chargeBalanceTest")
    class chargeBalanceTest {
        @Test
        @DisplayName("사용자의 잔액이 이미 존재하는 경우, 잔액을 충전하고, 잔액 충전 이력을 저장 메서드를 호출한다.")
        void 충전() {
            // Given
            Long balanceId = 1L;
            Long userId = 1L;
            long currentBalance = 3000L;
            long amount = 5000L;

            Balance existingBalance = new Balance(balanceId, userId, currentBalance);
            when(balanceRepository.findFirstByUserIdWithLock(userId)).thenReturn(Optional.of(existingBalance));

            // When
            BalanceChargeResult result = balanceService.chargeBalance(userId, amount);

            //Then
            assertEquals(amount + currentBalance, result.getFinalBalance());
            verify(balanceHistoryRepository, times(1)).save(any());
        }

        @Test
        @DisplayName("사용자의 잔액이 존재하지 않는 경우, BalanceException 에러 발생 및 잔액 업데이트를 호출하지 않는다.")
        void 사용자_잔액_존재하지_않음 () {
            //  Given
            long userId = 1L;
            long amount = 5000L;

            when(balanceRepository.findFirstByUserIdWithLock(userId)).thenReturn(Optional.empty());

            // When & Then
            BalanceException exception = assertThrows(BalanceException.class, () ->
                    balanceService.chargeBalance(userId, amount)
            );

            assertEquals(BalanceErrorCode.BALANCE_NOT_FOUND.getMessage(), exception.getMessage());
            verify(balanceRepository, times(0)).save(any());
        }

        @Test
        @DisplayName("최소 금액 이하로 충전 요청할 경우, BalanceException 에러 발생 및 잔액 업데이트를 호출하지 않는다.")
        void 최소_충전 () {
            // Given
            Long balanceId = 1L;
            long userId = 1L;
            long invalidAmount = 5L;
            Long currentBalance = 3000L;

            Balance existingBalance = new Balance(balanceId, userId, currentBalance);
            when(balanceRepository.findFirstByUserIdWithLock(userId)).thenReturn(Optional.of(existingBalance));

            // When & Then
            BalanceException exception = assertThrows(BalanceException.class, () ->
                    balanceService.chargeBalance(userId, invalidAmount)
            );
            assertEquals(BalanceErrorCode.INVALID_AMOUNT.getMessage(), exception.getMessage());
            verify(balanceRepository, times(0)).save(any());
        }

        @Test
        @DisplayName("충전 후 잔액이 최대 잔액을 넘을 경우, BalanceException 에러 발생 및 잔액 업데이트를 호출하지 않는다.")
        void 초과_충전 () {
            // Given
            Long balanceId = 1L;
            Long userId = 1L;
            Long currentBalance = 9999999L;
            long amount = 5000L;

            Balance existingBalance = new Balance(balanceId, userId, currentBalance);
            when(balanceRepository.findFirstByUserIdWithLock(userId)).thenReturn(Optional.of(existingBalance));

            // When & Then
            BalanceException exception = assertThrows(BalanceException.class, () ->
                    balanceService.chargeBalance(userId, amount)
            );

            assertEquals(BalanceErrorCode.EXCEEDS_MAX_BALANCE.getMessage(), exception.getMessage());

            verify(balanceRepository, times(0)).save(any());
        }
    }

    @Nested
    @DisplayName("getBalanceTest")
    class getBalanceTest {
        @Test
        @DisplayName("사용자 ID를 입력한 경우 사용자의 잔액을 반환한다.")
        void 존재하는_사용자의_잔액_조회() {
            // Given
            Long balanceId = 1L;
            Long userId = 1L;
            Long currentBalance = 3000L;
            Balance existingBalance = new Balance(balanceId, userId, currentBalance);
            when(balanceRepository.findFirstByUserId(userId)).thenReturn(Optional.of(existingBalance));

            // When
            BalanceResult result = balanceService.getBalance(userId);

            // Then
            assertNotNull(result);
            assertEquals(currentBalance, result.getBalance());
        }

        @Test
        @DisplayName("존재하지 않는 사용자 ID를 입력한 경우 BalanceException을 반환한다.")
        void 존재하지않는_사용자의_잔액_조회() {
            // Given
            long userId = 1L;
            when(balanceRepository.findFirstByUserId(userId)).thenReturn(Optional.empty());

            // When&Then
            BalanceException exception =  assertThrows(BalanceException.class, () -> {
                balanceService.getBalance(userId);
            });
            assertEquals(BalanceErrorCode.BALANCE_NOT_FOUND.getMessage(), exception.getMessage());
        }
    }
}
