package kr.naeseonja.be.server.queue.presentation.dto.response;

import kr.naeseonja.be.server.queue.domain.dto.QueueTokenPositionResult;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QueueTokenPositionResponse {
    private String token;
    private Long position;
    private boolean isAvailable;

    public QueueTokenPositionResponse(String token, Long position, boolean isAvailable) {
        this.token = token;
        this.position = position;
        this.isAvailable = isAvailable;
    }

    public static QueueTokenPositionResponse from(QueueTokenPositionResult queueTokenPositionResult) {
        return new QueueTokenPositionResponse(queueTokenPositionResult.getToken(), queueTokenPositionResult.getPosition(), queueTokenPositionResult.isAvailable());
    }
}
