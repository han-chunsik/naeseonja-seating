package kr.hhplus.be.server.queue.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QueueTokenResult {
    private Long userId;
    private String token;

    public QueueTokenResult(Long userId, String token) {
        this.userId = userId;
        this.token = token;
    }
}
