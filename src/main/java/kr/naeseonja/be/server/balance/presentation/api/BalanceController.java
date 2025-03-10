package kr.naeseonja.be.server.balance.presentation.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import kr.naeseonja.be.server.balance.domain.dto.BalanceChargeResult;
import kr.naeseonja.be.server.balance.domain.dto.BalanceResult;
import kr.naeseonja.be.server.balance.domain.service.BalanceService;
import kr.naeseonja.be.server.balance.presentation.dto.request.BalanceChargeRequest;
import kr.naeseonja.be.server.balance.presentation.dto.response.BalanceChargeResponse;
import kr.naeseonja.be.server.balance.presentation.dto.response.BalanceResponse;
import kr.naeseonja.be.server.common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/balance")
@Tag(name = "잔액 관리", description = "잔액 충전/조회")
public class BalanceController {

    private final BalanceService balanceService;

    @Operation(
            summary = "잔액 충전",
            description = "사용자의 잔액을 충전하고, 충전 이력을 저장한다. 사용자가 없는 경우 잔액을 생성하여 충전한다."
    )
    @PostMapping("/charge")
    public CommonResponse<BalanceChargeResponse> chargeBalance(@Valid @RequestBody BalanceChargeRequest request) {
        long userId = request.getUserId();
        long amount = request.getAmount();

        BalanceChargeResult balanceChargeResult =  balanceService.chargeBalance(userId, amount);
        BalanceChargeResponse response = BalanceChargeResponse.from(balanceChargeResult);
        return new CommonResponse<BalanceChargeResponse>().success(response);
    }

    @Operation(
            summary = "잔액 조회",
            description = "사용자의 잔액을 조회한다."
    )
    @GetMapping("/{userId}")
    public CommonResponse<BalanceResponse> getBalance(@PathVariable("userId") @Positive(message = "사용자 ID는 음수일 수 없습니다.") long userId){
        BalanceResult balanceResult = balanceService.getBalance(userId);
        BalanceResponse response = BalanceResponse.from(balanceResult);
        return new CommonResponse<BalanceResponse>().success(response);
    }
}
