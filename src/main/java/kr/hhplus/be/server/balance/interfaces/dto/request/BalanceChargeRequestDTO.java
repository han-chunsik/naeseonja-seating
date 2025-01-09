package kr.hhplus.be.server.balance.interfaces.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BalanceChargeRequestDTO {
    private long userId;

    @Positive(message = "양수만 가능합니다.")
    private long amount;

    @Builder
    public BalanceChargeRequestDTO(long userId, long amount) {
        this.userId = userId;
        this.amount = amount;
    }
}
