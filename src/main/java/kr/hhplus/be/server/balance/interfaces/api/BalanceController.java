package kr.hhplus.be.server.balance.interfaces.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.hhplus.be.server.balance.domain.service.BalanceService;
import kr.hhplus.be.server.balance.domain.dto.BalanceResult;
import kr.hhplus.be.server.balance.interfaces.dto.response.BalanceResponse;
import kr.hhplus.be.server.common.code.SuccessCode;
import kr.hhplus.be.server.balance.domain.dto.BalanceChargeResult;
import kr.hhplus.be.server.balance.interfaces.dto.request.BalanceChargeRequest;
import kr.hhplus.be.server.balance.interfaces.dto.response.BalanceChargeResponse;
import kr.hhplus.be.server.common.dto.CommonResponse;
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
        BalanceChargeResult balanceChargeResult =  balanceService.chargeBalance(request.getUserId(), request.getAmount());

        BalanceChargeResponse response = BalanceChargeResponse.builder()
                .userId(balanceChargeResult.getUserId())
                .amount(balanceChargeResult.getAmount())
                .finalBalance(balanceChargeResult.getFinalBalance())
                .build();

        return new CommonResponse<>(SuccessCode.SUCCESS.getCode(), SuccessCode.SUCCESS.getMessage(), response);
    }

    @Operation(
            summary = "잔액 조회",
            description = "사용자의 잔액을 조회한다."
    )
    @GetMapping("/{userId}")
    public CommonResponse<BalanceResponse> getBalance(@PathVariable long userId){
        BalanceResult balanceResult = balanceService.getBalance(userId);

        BalanceResponse response = BalanceResponse.builder()
                .userId(balanceResult.getUserId())
                .balance(balanceResult.getBalance())
                .build();
        return new CommonResponse<>(SuccessCode.SUCCESS.getCode(), SuccessCode.SUCCESS.getMessage(), response);
    }
}
