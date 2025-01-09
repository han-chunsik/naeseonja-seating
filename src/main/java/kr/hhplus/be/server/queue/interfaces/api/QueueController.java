package kr.hhplus.be.server.queue.interfaces.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.hhplus.be.server.common.code.SuccessCode;
import kr.hhplus.be.server.common.dto.CommonResponse;
import kr.hhplus.be.server.queue.domain.dto.QueueTokenPositionResult;
import kr.hhplus.be.server.queue.domain.dto.QueueTokenResult;
import kr.hhplus.be.server.queue.domain.service.QueueService;
import kr.hhplus.be.server.queue.interfaces.dto.request.QueueTokenRequest;
import kr.hhplus.be.server.queue.interfaces.dto.response.QueueTokenPositionResponse;
import kr.hhplus.be.server.queue.interfaces.dto.response.QueueTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/queue")
@Tag(name = "대기열 토큰", description = "대기열 토큰 발급/순서 조회")
public class QueueController {

    private final QueueService queueService;

    @Operation(
            summary = "대기열 토큰 발급",
            description = "대기열에 진입하기 위해 대기열 토큰을 발급받는다."
    )
    @PostMapping("/token")
    public CommonResponse<QueueTokenResponse> createQueueToken(@Valid @RequestBody QueueTokenRequest request) {
        QueueTokenResult queueTokenResult =  queueService.createToken(request.getUserId());

        QueueTokenResponse response = QueueTokenResponse.builder()
                .userId(queueTokenResult.getUserId())
                .token(queueTokenResult.getToken())
                .build();

        return new CommonResponse<>(SuccessCode.SUCCESS.getCode(), SuccessCode.SUCCESS.getMessage(), response);
    }

    @Operation(
            summary = "대기열 토큰 순서 조회",
            description = "인증된 Bearer 토큰을 통해 고정된 대기열 순서를 반환한다."
    )
    @GetMapping("/token/position")
    public CommonResponse<QueueTokenPositionResponse> getQueueTokenPosition(@RequestHeader("Authorization") String token) {
        QueueTokenPositionResult queueTokenPositionResult = queueService.getTokenPosition(token);

        QueueTokenPositionResponse response = QueueTokenPositionResponse.builder()
                .position(queueTokenPositionResult.getPosition())
                .token(queueTokenPositionResult.getToken())
                .isAvailable(queueTokenPositionResult.isAvailable())
                .build();

        return new CommonResponse<>(SuccessCode.SUCCESS.getCode(), SuccessCode.SUCCESS.getMessage(), response);
    }
}