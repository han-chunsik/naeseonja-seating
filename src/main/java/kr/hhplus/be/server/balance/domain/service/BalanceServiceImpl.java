package kr.hhplus.be.server.balance.domain.service;

import kr.hhplus.be.server.balance.domain.entity.Balance;
import kr.hhplus.be.server.balance.domain.repository.BalanceRepository;
import kr.hhplus.be.server.balance.domain.vo.BalanceChargeVO;
import kr.hhplus.be.server.balance.domain.vo.BalanceVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {

    private final BalanceRepository balanceRepository;
    private final BalanceChargeValidatorService balanceChargeValidatorService;
    private final BalanceHistoryService balanceHistoryService;

    @Transactional
    @Override
    public BalanceChargeVO chargeBalance(Long userId, Long amount) {
        // 1. 최소 충전 유효성 확인
        balanceChargeValidatorService.validateChargeAmount(amount);

        // 2. 사용자 잔액 확인
        Balance userBalance = balanceRepository.findFirstByUserIdWithLock(userId);

        // 3. 사용자 잔액이 없는 경우 0 으로 잔액 생성
        if (userBalance == null) {
            userBalance = new Balance();
            userBalance.create(userId, 0L);
            balanceRepository.save(userBalance);
        }

        // 4. 잔액 충전
        Long currentUserBalance = userBalance.getBalance();
        long updateBalance = currentUserBalance + amount;

        // 5. 최대 보유 잔액 유효성 확인
        balanceChargeValidatorService.validateMaxBalance(updateBalance);

        // 6. 잔액 충전
        userBalance.changeBalance(updateBalance);
        balanceRepository.save(userBalance);

        // 6. 잔액 충전 히스토리 저장
        balanceHistoryService.saveBalanceChargeHistory(userId, amount);

        return BalanceChargeVO.builder()
                .userId(userId)
                .amount(amount)
                .finalBalance(updateBalance)
                .build();
    }

    @Override
    public BalanceVO getBalance(Long userId) {
        Balance userBalance = balanceRepository.findFirstByUserId(userId);
        if (userBalance == null) {
            throw new RuntimeException("User not found with id: " + userId);
        }
        Long currentUserBalance = userBalance.getBalance();

        return BalanceVO.builder()
                .userId(userId)
                .balance(currentUserBalance)
                .build();
    }
}
