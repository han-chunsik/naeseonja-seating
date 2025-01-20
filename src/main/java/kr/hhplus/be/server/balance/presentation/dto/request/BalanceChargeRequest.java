package kr.hhplus.be.server.balance.presentation.dto.request;


import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BalanceChargeRequest {
    @Positive(message = "사용자 ID는 음수일 수 없습니다.")
    private long userId;

    @Positive(message = "충전 금액은 음수일 수 없습니다.")
    private long amount;
}
