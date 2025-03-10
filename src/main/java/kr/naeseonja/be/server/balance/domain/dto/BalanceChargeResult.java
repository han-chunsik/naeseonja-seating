package kr.naeseonja.be.server.balance.domain.dto;

import kr.naeseonja.be.server.balance.domain.model.Balance;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BalanceChargeResult {
    private long userId;
    private long amount;
    private long finalBalance;

    public BalanceChargeResult(long userId, long amount, long finalBalance) {
        this.userId = userId;
        this.amount = amount;
        this.finalBalance = finalBalance;
    }

    public static BalanceChargeResult fromWithAmount(Balance balance, long amount) {
        return new BalanceChargeResult(balance.getUserId(), amount, balance.getBalance());
    }
}
