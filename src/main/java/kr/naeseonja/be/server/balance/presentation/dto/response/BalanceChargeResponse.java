package kr.naeseonja.be.server.balance.presentation.dto.response;

import kr.naeseonja.be.server.balance.domain.dto.BalanceChargeResult;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BalanceChargeResponse {
    private long userId;
    private long amount;
    private long finalBalance;

    public BalanceChargeResponse(long userId, long amount, long finalBalance) {
        this.userId = userId;
        this.amount = amount;
        this.finalBalance = finalBalance;
    }

    public static BalanceChargeResponse from(BalanceChargeResult balanceChargeResult) {
        return new BalanceChargeResponse(balanceChargeResult.getUserId(), balanceChargeResult.getAmount(), balanceChargeResult.getFinalBalance());
    }
}
