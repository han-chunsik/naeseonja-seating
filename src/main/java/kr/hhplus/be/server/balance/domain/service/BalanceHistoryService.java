package kr.hhplus.be.server.balance.domain.service;

import kr.hhplus.be.server.balance.domain.entity.BalanceHistory;
import kr.hhplus.be.server.balance.domain.repository.BalanceHistoryRepository;
import org.springframework.stereotype.Service;

@Service
public class BalanceHistoryService {
    private final BalanceHistoryRepository balanceHistoryRepository;

    public BalanceHistoryService(BalanceHistoryRepository balanceHistoryRepository) {
        this.balanceHistoryRepository = balanceHistoryRepository;
    }

    public void saveBalanceChargeHistory(Long userId, Long amount) {
        BalanceHistory balanceHistory = BalanceHistory.createBalanceHistory(userId, amount, BalanceHistory.Type.CHARGE);
        balanceHistoryRepository.save(balanceHistory);
    }

    public void saveBalanceUseHistory(Long userId, Long amount) {
        BalanceHistory balanceHistory = BalanceHistory.createBalanceHistory(userId, amount, BalanceHistory.Type.USE);
        balanceHistoryRepository.save(balanceHistory);
    }
}
