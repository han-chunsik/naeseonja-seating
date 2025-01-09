package kr.hhplus.be.server.balance.domain.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BalanceChargeVO {
    private long userId;
    private long amount;
    private long finalBalance;

    @Builder
    public BalanceChargeVO(long userId, long amount, long finalBalance) {
        this.userId = userId;
        this.amount = amount;
        this.finalBalance = finalBalance;
    }
}
