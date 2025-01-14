package kr.hhplus.be.server.queue.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QueueTokenPositionResponse {
    private String token;
    private Long position;
    private boolean isAvailable;

    @Builder
    public QueueTokenPositionResponse(String token, Long position, boolean isAvailable) {
        this.token = token;
        this.position = position;
        this.isAvailable = isAvailable;
    }
}
