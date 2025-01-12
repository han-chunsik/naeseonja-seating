package kr.hhplus.be.server.balance.interfaces.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BalanceResponse {
    private long userId;
    private long balance;

    @Builder
    public BalanceResponse(long userId, long balance) {
        this.userId = userId;
        this.balance = balance;
    }
}
