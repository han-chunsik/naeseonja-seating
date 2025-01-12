package kr.hhplus.be.server.balance;

import kr.hhplus.be.server.balance.domain.model.Balance;
import kr.hhplus.be.server.balance.domain.repository.BalanceRepository;
import kr.hhplus.be.server.balance.domain.service.BalanceService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers
@Slf4j
public class BalanceIntegrationTest {
    @Autowired
    private BalanceService balanceService;

    @Autowired
    private BalanceRepository balanceRepository;

    @Nested
    @DisplayName("동시성 제어 통합 테스트")
    class ConcurrencyTest {
        @Test
        @DisplayName("동일한 사용자 충전이 2번 이상 발생할 경우 데이터 정합성 확인")
        public void 동일한_사용자_충전() throws Exception {
            //Given
            int threadCount = 2;
            long userId = 2L;
            long[] amounts = {1000L, 2000L};

            CountDownLatch doneSignal = new CountDownLatch(threadCount);
            ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

            Balance currentBalance = balanceRepository.findFirstByUserId(userId).get();

            //When
            for (int i = 0; i < threadCount; i++) {
                long amount = amounts[i];
                executorService.execute(() -> {
                    try {
                        balanceService.chargeBalance(userId, amount);
                    } catch (Exception e) {
                        log.error("An error occurred: ", e);
                    } finally {
                        doneSignal.countDown();
                    }
                });
            }
            doneSignal.await();
            executorService.shutdown();

            //Then
            Balance finalBalance = balanceRepository.findFirstByUserId(userId).get();
            long totalChargeAmount = Arrays.stream(amounts).sum();
            long expectedBalance = currentBalance.getBalance() + totalChargeAmount;
            log.info("충전 전 잔액: {}", currentBalance.getBalance());
            log.info("충전 요청 잔액: {}", totalChargeAmount);
            log.info("충전 후 예상 잔액: {}", expectedBalance);
            assertEquals(expectedBalance, finalBalance.getBalance());
        }
    }
}
