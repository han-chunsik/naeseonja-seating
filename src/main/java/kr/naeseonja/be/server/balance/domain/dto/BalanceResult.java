package kr.naeseonja.be.server.balance.domain.dto;

import kr.naeseonja.be.server.balance.domain.model.Balance;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BalanceResult {
    private long userId;
    private long balance;

    public BalanceResult(long userId, long balance) {
        this.userId = userId;
        this.balance = balance;
    }

    public static BalanceResult from(Balance balance) {
        return new BalanceResult(balance.getUserId(), balance.getBalance());
    }
}
