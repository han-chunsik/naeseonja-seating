package kr.hhplus.be.server.balance.domain.service;

import kr.hhplus.be.server.balance.domain.entity.Balance;
import kr.hhplus.be.server.balance.domain.entity.BalanceHistory;
import kr.hhplus.be.server.balance.domain.repository.BalanceHistoryRepository;
import kr.hhplus.be.server.balance.domain.repository.BalanceRepository;
import kr.hhplus.be.server.balance.domain.dto.BalanceChargeResult;
import kr.hhplus.be.server.balance.domain.dto.BalanceResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class BalanceService {

    private final BalanceRepository balanceRepository;
    private final BalanceHistoryRepository balanceHistoryRepository;

    @Transactional
    public BalanceChargeResult chargeBalance(Long userId, Long amount) {
        // 1. 최소 충전 유효성 확인
        if (amount < BalanceLimit.BALANCE_RECHARGE_LIMIT_MIN.getLimit()) {
            throw new IllegalArgumentException("최소 충전 잔액은 " + BalanceLimit.BALANCE_RECHARGE_LIMIT_MIN.getLimit() + "입니다.");
        }

        // 2. 사용자 잔액 확인
        Balance userBalance = balanceRepository.findFirstByUserIdWithLock(userId);

        // 3. 사용자 잔액이 없는 경우 0 으로 잔액 생성
        if (userBalance == null) {
            userBalance = new Balance();
            userBalance.create(userId, 0L);
            balanceRepository.save(userBalance);
        }

        // 4. 잔액 충전
        Long userBalanceId = userBalance.getId();
        Long currentUserBalance = userBalance.getBalance();
        long updateBalance = currentUserBalance + amount;

        // 5. 최대 보유 잔액 유효성 확인
        if (updateBalance > BalanceLimit.BALANCE_LIMIT_MAX.getLimit()) {
            throw new IllegalArgumentException("충전 후 잔액이 " + BalanceLimit.BALANCE_LIMIT_MAX.getLimit() + "를 초과할 수 없습니다.");
        }

        // 6. 잔액 충전
        userBalance.changeBalance(updateBalance);
        balanceRepository.save(userBalance);

        // 6. 잔액 충전 히스토리 저장
        BalanceHistory balanceHistory = BalanceHistory.createBalanceHistory(userBalanceId, amount, BalanceHistory.Type.CHARGE);
        balanceHistoryRepository.save(balanceHistory);

        return BalanceChargeResult.builder()
                .userId(userId)
                .amount(amount)
                .finalBalance(updateBalance)
                .build();
    }

    @Transactional(readOnly = true)
    public BalanceResult getBalance(Long userId) {
        Balance userBalance = balanceRepository.findFirstByUserId(userId);
        if (userBalance == null) {
            throw new RuntimeException("User not found with id: " + userId);
        }
        Long currentUserBalance = userBalance.getBalance();

        return BalanceResult.builder()
                .userId(userId)
                .balance(currentUserBalance)
                .build();
    }
}
