package kr.hhplus.be.server.queue.domain;

import kr.hhplus.be.server.queue.domain.dto.QueueTokenResult;
import kr.hhplus.be.server.queue.domain.model.QueueToken;
import kr.hhplus.be.server.queue.domain.repository.QueueTokenRepository;
import kr.hhplus.be.server.queue.domain.service.QueueTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class QueueTokenServiceTest {
    @InjectMocks
    QueueTokenService queueTokenService;

    @Mock
    QueueTokenRepository queueTokenRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);  // Mockito 초기화
    }

    @Nested
    @DisplayName("createTokenTest")
    class createTokenTest {
        @Test
        @DisplayName("유효한 토큰이 없는 경우 토큰을 반환하고 저장한다.")
        void 신규_토큰_생성() {
            //  Given
            Long userId = 1L;

            when(queueTokenRepository.findFirstByUserIdAndStatusNotWithLock(userId, QueueToken.Status.EXPIRED))
                    .thenReturn(null);

            // When
            QueueTokenResult result = queueTokenService.createToken(userId);

            // Then
            assertEquals(userId, result.getUserId());
            assertNotNull(result.getToken());
            verify(queueTokenRepository, times(1)).save(any(QueueToken.class));
        }

        @Test
        @DisplayName("유효한 토큰이 있는 경우 만료 후 발급한다.")
        void 만료_후_토큰_생성 () {
            // Given
            Long userId = 1L;

            QueueToken existingToken = new QueueToken(1L, userId, "token", QueueToken.Status.WAITING, LocalDateTime.now(), null);

            when(queueTokenRepository.findFirstByUserIdAndStatusNotWithLock(userId, QueueToken.Status.EXPIRED))
                    .thenReturn(existingToken);

            // When
            QueueTokenResult result = queueTokenService.createToken(userId);

            // Then
            assertEquals(userId, result.getUserId());
            assertNotNull(result.getToken());
            verify(queueTokenRepository, times(2)).save(any(QueueToken.class));


        }
    }
}
