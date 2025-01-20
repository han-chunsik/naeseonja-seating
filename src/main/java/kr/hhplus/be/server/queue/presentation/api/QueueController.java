package kr.hhplus.be.server.queue.presentation.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.hhplus.be.server.common.code.SuccessCode;
import kr.hhplus.be.server.common.dto.CommonResponse;
import kr.hhplus.be.server.queue.domain.dto.QueueTokenPositionResult;
import kr.hhplus.be.server.queue.domain.dto.QueueTokenResult;
import kr.hhplus.be.server.queue.domain.service.QueueTokenService;
import kr.hhplus.be.server.queue.presentation.dto.request.QueueTokenRequest;
import kr.hhplus.be.server.queue.presentation.dto.response.QueueTokenPositionResponse;
import kr.hhplus.be.server.queue.presentation.dto.response.QueueTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/queue")
@Tag(name = "대기열 토큰", description = "대기열 토큰 발급/순서 조회")
public class QueueController {

    private static final String BEARER_PREFIX = "Bearer ";

    private final QueueTokenService queueTokenService;

    @Operation(
            summary = "대기열 토큰 발급",
            description = "대기열에 진입하기 위해 대기열 토큰을 발급받는다."
    )
    @PostMapping("/token")
    public CommonResponse<QueueTokenResponse> createQueueToken(@Valid @RequestBody QueueTokenRequest request) {
        QueueTokenResult queueTokenResult =  queueTokenService.createToken(request.getUserId());
        QueueTokenResponse response = QueueTokenResponse.from(queueTokenResult);
        return new CommonResponse<>(SuccessCode.SUCCESS.getCode(), SuccessCode.SUCCESS.getMessage(), response);
    }

    @Operation(
            summary = "대기열 토큰 순서 조회",
            description = "인증된 Bearer 토큰을 통해 고정된 대기열 순서를 반환한다."
    )
    @GetMapping("/token/position")
    public CommonResponse<QueueTokenPositionResponse> getQueueTokenPosition(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(BEARER_PREFIX.length()).trim();
        QueueTokenPositionResult queueTokenPositionResult = queueTokenService.getTokenPosition(token);
        QueueTokenPositionResponse response = QueueTokenPositionResponse.from(queueTokenPositionResult);
        return new CommonResponse<>(SuccessCode.SUCCESS.getCode(), SuccessCode.SUCCESS.getMessage(), response);
    }
}