package kr.hhplus.be.server.queue.domain;

import kr.hhplus.be.server.queue.domain.model.QueueToken;
import kr.hhplus.be.server.queue.exception.QueueErrorCode;
import kr.hhplus.be.server.queue.exception.QueueException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class QueueTokenTest {
    @Nested
    @DisplayName("getPositionByTokenTest")
    class getPositionByTokenTest {
        @Test
        @DisplayName("WAITING 상태 토큰 목록과 토큰id를 입력하면, 해당 토큰의 순서를 반환한다.")
        void 토큰_순서_반환() {
            // Given
            QueueToken token1 = new QueueToken(null, 1L, "token1", QueueToken.Status.WAITING, LocalDateTime.now(), null);
            QueueToken token2 = new QueueToken(null, 2L, "token2", QueueToken.Status.WAITING, LocalDateTime.now(), null);
            QueueToken token3 = new QueueToken(null, 3L, "token3", QueueToken.Status.WAITING, LocalDateTime.now(), null);
            List<QueueToken> tokens = List.of(token1, token2, token3);

            // When
            long position = QueueToken.getPositionByToken(tokens, "token2");

            // Then
            assertEquals(2, position);
        }
    }
    @Nested
    @DisplayName("validateExpiredTokenTest")
    class validateExpiredTokenTest {
        @Test
        @DisplayName("토큰이 만료 상태일 경우 QueueException을 반환한다.")
        void 만료_토큰_확인() {
            //  Given
            QueueToken token1 = new QueueToken(null, 1L, "token1", QueueToken.Status.EXPIRED, LocalDateTime.now(), null);
            // When&Then
            QueueException exception = assertThrows(QueueException.class, token1::validateExpiredToken);
            assertEquals(QueueErrorCode.EXPIRED_TOKEN.getMessageWithArgs("token1"), exception.getMessage());
        }
    }

    @Nested
    @DisplayName("createTokenTest")
    class createTokenTest {
        @Test
        @DisplayName("사용자ID를 입력하면 Waiting상태의 토큰을 반환한다.")
        void 토큰_발급() {
            // Given
            Long userId = 123L;

            // When
            QueueToken token = QueueToken.createToken(userId);

            // Then
            assertEquals(QueueToken.Status.WAITING, token.getStatus());
            assertEquals(userId, token.getUserId());
        }
    }
}
