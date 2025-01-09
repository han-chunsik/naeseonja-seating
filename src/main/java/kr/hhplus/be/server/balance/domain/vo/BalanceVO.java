package kr.hhplus.be.server.balance.domain.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BalanceVO {
    private long userId;
    private long balance;

    @Builder
    public BalanceVO(long userId, long balance) {
        this.userId = userId;
        this.balance = balance;
    }
}
