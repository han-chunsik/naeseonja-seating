package kr.hhplus.be.server.balance.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BalanceChargeResult {
    private long userId;
    private long amount;
    private long finalBalance;

    @Builder
    public BalanceChargeResult(long userId, long amount, long finalBalance) {
        this.userId = userId;
        this.amount = amount;
        this.finalBalance = finalBalance;
    }
}
