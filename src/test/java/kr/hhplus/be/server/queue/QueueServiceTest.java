package kr.hhplus.be.server.queue;

import kr.hhplus.be.server.queue.domain.dto.QueueTokenPositionResult;
import kr.hhplus.be.server.queue.domain.dto.QueueTokenResult;
import kr.hhplus.be.server.queue.domain.entity.QueueToken;
import kr.hhplus.be.server.queue.domain.repository.QueueTokenRepository;
import kr.hhplus.be.server.queue.domain.service.QueueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class QueueServiceTest {
    @InjectMocks
    QueueService queueService;

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
        @DisplayName("성공: 유효한 토큰이 없는 경우 토큰 발급")
        void 신규_토큰_생성() {
            //  Given
            Long userId = 1L;

            when(queueTokenRepository.findFirstByUserIdAndStatusNotWithLock(userId, QueueToken.Status.EXPIRED))
                    .thenReturn(null);

            // When
            QueueTokenResult result = queueService.createToken(userId);

            // Then
            assertEquals(userId, result.getUserId());
            assertNotNull(result.getToken());
            verify(queueTokenRepository, times(1)).save(any(QueueToken.class));
        }

        @Test
        @DisplayName("성공: 유효한 토큰이 있는 경우 만료 후 발급")
        void 만료_후_토큰_생성 () {
            // Given
            Long userId = 1L;

            QueueToken existingToken = new QueueToken(1L, userId, "token", QueueToken.Status.WAITING, LocalDateTime.now(), null);

            when(queueTokenRepository.findFirstByUserIdAndStatusNotWithLock(userId, QueueToken.Status.EXPIRED))
                    .thenReturn(existingToken);

            // When
            QueueTokenResult result = queueService.createToken(userId);

            // Then
            assertEquals(userId, result.getUserId());
            assertNotNull(result.getToken());
            verify(queueTokenRepository, times(2)).save(any(QueueToken.class));


        }
    }

    @Nested
    @DisplayName("getTokenPositionTest")
    class getTokenPositionTest {
        @Test
        @DisplayName("실패: 유효하지 않은 토큰 사용 시 RuntimeException 에러 발생")
        void 유효하지_않은_토큰() {
            // Given
            when(queueTokenRepository.findFirstByToken("invalidToken")).thenReturn(null);

            // When & Then
            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                queueService.getTokenPosition("invalidToken");
            });
            assertEquals("유효하지 않은 토큰입니다.", exception.getMessage());
        }

        @Test
        @DisplayName("실패: 만료된 토큰 사용 시 RuntimeException 에러 발생")
        void 만료_토큰() {
            // Given
            QueueToken expiredToken = QueueToken.builder()
                    .id(2L)
                    .userId(2L)
                    .token("expiredToken")
                    .status(QueueToken.Status.EXPIRED)
                    .createdAt(LocalDateTime.now().minusDays(1))
                    .activatedAt(LocalDateTime.now().minusDays(1))
                    .build();

            when(queueTokenRepository.findFirstByToken("expiredToken")).thenReturn(expiredToken);

            // When & Then
            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                queueService.getTokenPosition("expiredToken");
            });
            assertEquals("만료된 토큰입니다.", exception.getMessage());
        }

        @Test
        @DisplayName("성공: 대기 상태의 토큰 사용 시 순서 반환")
        void 대기_토큰() {
            // Given
            QueueToken waitingToken = QueueToken.builder()
                    .id(3L)
                    .userId(3L)
                    .token("waitingToken")
                    .status(QueueToken.Status.WAITING)
                    .createdAt(LocalDateTime.now())
                    .activatedAt(LocalDateTime.now())
                    .build();

            when(queueTokenRepository.findFirstByToken("waitingToken")).thenReturn(waitingToken);
            when(queueTokenRepository.findQueueTokenEntitiesByStatus(QueueToken.Status.WAITING))
                    .thenReturn(List.of(waitingToken)); // 대기 중인 토큰 리스트

            // When
            QueueTokenPositionResult result = queueService.getTokenPosition("waitingToken");

            // Then
            assertNotNull(result);
            assertEquals(1, result.getPosition());
            assertFalse(result.isAvailable());
        }

        @Test
        @DisplayName("성공: 활성 상태의 토큰 사용 시 활성 여부 true 반환")
        void 활성_토큰() {
            // Given
            QueueToken availableToken = QueueToken.builder()
                    .id(4L)
                    .userId(4L)
                    .token("availableToken")
                    .status(QueueToken.Status.AVAILABLE)
                    .createdAt(LocalDateTime.now())
                    .activatedAt(LocalDateTime.now())
                    .build();

            when(queueTokenRepository.findFirstByToken("availableToken")).thenReturn(availableToken);

            // when
            QueueTokenPositionResult result = queueService.getTokenPosition("availableToken");

            // then
            assertNotNull(result);
            assertNull(result.getPosition());
            assertTrue(result.isAvailable());
        }
    }
}
