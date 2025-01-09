package kr.hhplus.be.server.balance.interfaces.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BalanceChargeResponse {
    private long userId;
    private long amount;
    private long finalBalance;

    @Builder
    public BalanceChargeResponse(long userId, long amount, long finalBalance) {
        this.userId = userId;
        this.amount = amount;
        this.finalBalance = finalBalance;
    }
}
