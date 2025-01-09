package kr.hhplus.be.server.queue.interfaces.dto.request;

import lombok.Builder;

@Builder
public record IssueQueueTokenRequest(
    Long userId
) {
}
