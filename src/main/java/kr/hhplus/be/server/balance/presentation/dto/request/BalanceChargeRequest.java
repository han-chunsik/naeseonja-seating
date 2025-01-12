package kr.hhplus.be.server.balance.presentation.dto.request;


import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BalanceChargeRequest {
    @Positive(message = "{balance.validation.user.id.invalid}")
    private long userId;

    @Positive(message = "{balance.validation.amount.id.invalid}")
    private long amount;
}
