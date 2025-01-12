package kr.hhplus.be.server.balance.presentation.dto.response;

import kr.hhplus.be.server.balance.domain.dto.BalanceResult;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BalanceResponse {
    private long userId;
    private long balance;

    public BalanceResponse(long userId, long balance) {
        this.userId = userId;
        this.balance = balance;
    }

    public static BalanceResponse from(BalanceResult balanceResult) {
        return new BalanceResponse(balanceResult.getUserId(), balanceResult.getBalance());
    }
}
