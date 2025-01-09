package kr.hhplus.be.server.balance.interfaces.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BalanceResponseDTO {
    private long userId;
    private long balance;

    @Builder
    public BalanceResponseDTO(long userId, long balance) {
        this.userId = userId;
        this.balance = balance;
    }
}
