package kr.hhplus.be.server.balance.interfaces.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.hhplus.be.server.balance.domain.service.BalanceService;
import kr.hhplus.be.server.balance.domain.vo.BalanceVO;
import kr.hhplus.be.server.balance.interfaces.dto.response.BalanceResponseDTO;
import kr.hhplus.be.server.common.code.SuccessCode;
import kr.hhplus.be.server.balance.domain.vo.BalanceChargeVO;
import kr.hhplus.be.server.balance.interfaces.dto.request.BalanceChargeRequestDTO;
import kr.hhplus.be.server.balance.interfaces.dto.response.BalanceChargeResponseDTO;
import kr.hhplus.be.server.common.dto.CommonResponseDTO;
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
    public CommonResponseDTO<BalanceChargeResponseDTO> chargeBalance(@Valid @RequestBody BalanceChargeRequestDTO request) {
        BalanceChargeVO balanceChargeVO =  balanceService.chargeBalance(request.getUserId(), request.getAmount());

        BalanceChargeResponseDTO responseDTO = BalanceChargeResponseDTO.builder()
                .userId(balanceChargeVO.getUserId())
                .amount(balanceChargeVO.getAmount())
                .finalBalance(balanceChargeVO.getFinalBalance())
                .build();

        return new CommonResponseDTO<>(SuccessCode.SUCCESS.getCode(), SuccessCode.SUCCESS.getMessage(), responseDTO);
    }

    @Operation(
            summary = "잔액 조회",
            description = "사용자의 잔액을 조회한다."
    )
    @GetMapping("/{userId}")
    public CommonResponseDTO<BalanceResponseDTO> getBalance(@PathVariable long userId){
        BalanceVO balanceVO = balanceService.getBalance(userId);

        BalanceResponseDTO responseDTO = BalanceResponseDTO.builder()
                .userId(balanceVO.getUserId())
                .balance(balanceVO.getBalance())
                .build();
        return new CommonResponseDTO<>(SuccessCode.SUCCESS.getCode(), SuccessCode.SUCCESS.getMessage(), responseDTO);
    }
}
