package kr.naeseonja.be.server.queue.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QueueTokenPositionResult {
    private String token;
    private Long position;
    private boolean isAvailable;

    public QueueTokenPositionResult(String token, Long position, boolean isAvailable) {
        this.token = token;
        this.position = position;
        this.isAvailable = isAvailable;
    }
}
