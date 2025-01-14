package kr.hhplus.be.server.queue.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QueueTokenResponse {
    private long userId;
    private String token;

    @Builder
    public QueueTokenResponse(long userId, String token) {
        this.userId = userId;
        this.token = token;
    }
}
