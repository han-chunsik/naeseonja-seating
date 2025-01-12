package kr.hhplus.be.server.queue.interfaces.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QueueTokenRequest {
    @Positive(message = "양수만 가능합니다.")
    private long userId;

    @Builder
    public QueueTokenRequest(long userId) {
        this.userId = userId;
    }
}
