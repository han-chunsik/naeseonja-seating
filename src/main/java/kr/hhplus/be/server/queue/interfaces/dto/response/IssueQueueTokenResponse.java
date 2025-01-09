package kr.hhplus.be.server.queue.interfaces.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record IssueQueueTokenResponse(
    Long id,
    Long userId,
    String token,
    String tokenStatus,
    LocalDateTime createdAt
) {
}
