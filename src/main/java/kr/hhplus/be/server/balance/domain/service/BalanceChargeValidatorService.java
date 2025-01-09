package kr.hhplus.be.server.balance.domain.service;

import org.springframework.stereotype.Service;

@Service
public class BalanceChargeValidatorService {
    public void validateChargeAmount(Long amount) {
        if (amount < BalanceLimit.BALANCE_RECHARGE_LIMIT_MIN.getLimit()) {
            throw new IllegalArgumentException("최소 충전 잔액은 " + BalanceLimit.BALANCE_RECHARGE_LIMIT_MIN.getLimit() + "입니다.");
        }
    }

    public void validateMaxBalance(Long updateBalance) {
        if (updateBalance > BalanceLimit.BALANCE_LIMIT_MAX.getLimit()) {
            throw new IllegalArgumentException("충전 후 잔액이 " + BalanceLimit.BALANCE_LIMIT_MAX.getLimit() + "를 초과할 수 없습니다.");
        }
    }
}
