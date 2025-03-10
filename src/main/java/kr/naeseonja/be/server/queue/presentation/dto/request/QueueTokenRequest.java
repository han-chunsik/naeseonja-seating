package kr.naeseonja.be.server.queue.presentation.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QueueTokenRequest {
    @Positive(message = "사용자 ID는 음수일 수 없습니다.")
    private long userId;

    public QueueTokenRequest(long userId) {
        this.userId = userId;
    }
}
