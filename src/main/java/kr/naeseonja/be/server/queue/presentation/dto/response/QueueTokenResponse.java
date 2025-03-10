package kr.naeseonja.be.server.queue.presentation.dto.response;

import kr.naeseonja.be.server.queue.domain.dto.QueueTokenResult;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QueueTokenResponse {
    private long userId;
    private String token;

    public QueueTokenResponse(long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public static QueueTokenResponse from(QueueTokenResult queueTokenResult) {
        return new QueueTokenResponse(queueTokenResult.getUserId(), queueTokenResult.getToken());
    }
}
