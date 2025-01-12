package kr.hhplus.be.server.balance.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BalanceResult {
    private long userId;
    private long balance;

    @Builder
    public BalanceResult(long userId, long balance) {
        this.userId = userId;
        this.balance = balance;
    }
}
