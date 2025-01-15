package kr.hhplus.be.server.queue;

import kr.hhplus.be.server.queue.domain.dto.QueueTokenPositionResult;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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

//    @Nested
//    @DisplayName("createTokenTest")
//    class createTokenTest {
//        @Test
//        @DisplayName("성공: 유효한 토큰이 없는 경우 토큰 발급")
//        void 신규_토큰_생성() {
//            //  Given
//            Long userId = 1L;
//
//            when(queueTokenRepository.findFirstByUserIdAndStatusNotWithLock(userId, QueueToken.Status.EXPIRED))
//                    .thenReturn(null);
//
//            // When
//            QueueTokenResult result = queueTokenService.createToken(userId);
//
//            // Then
//            assertEquals(userId, result.getUserId());
//            assertNotNull(result.getToken());
//            verify(queueTokenRepository, times(1)).save(any(QueueToken.class));
//        }
//
//        @Test
//        @DisplayName("성공: 유효한 토큰이 있는 경우 만료 후 발급")
//        void 만료_후_토큰_생성 () {
//            // Given
//            Long userId = 1L;
//
//            QueueToken existingToken = new QueueToken(1L, userId, "token", QueueToken.Status.WAITING, LocalDateTime.now(), null);
//
//            when(queueTokenRepository.findFirstByUserIdAndStatusNotWithLock(userId, QueueToken.Status.EXPIRED))
//                    .thenReturn(existingToken);
//
//            // When
//            QueueTokenResult result = queueTokenService.createToken(userId);
//
//            // Then
//            assertEquals(userId, result.getUserId());
//            assertNotNull(result.getToken());
//            verify(queueTokenRepository, times(2)).save(any(QueueToken.class));
//
//
//        }
//    }
//
//    @Nested
//    @DisplayName("getTokenPositionTest")
//    class getTokenPositionTest {
//        @Test
//        @DisplayName("실패: 유효하지 않은 토큰 사용 시 RuntimeException 에러 발생")
//        void 유효하지_않은_토큰() {
//            // Given
//            when(queueTokenRepository.findFirstByToken("invalidToken")).thenReturn(null);
//
//            // When & Then
//            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
//                queueTokenService.getTokenPosition("invalidToken");
//            });
//            assertEquals("유효하지 않은 토큰입니다.", exception.getMessage());
//        }
//
//        @Test
//        @DisplayName("실패: 만료된 토큰 사용 시 RuntimeException 에러 발생")
//        void 만료_토큰() {
//            // Given
//            QueueToken expiredToken = QueueToken.builder()
//                    .id(2L)
//                    .userId(2L)
//                    .token("expiredToken")
//                    .status(QueueToken.Status.EXPIRED)
//                    .createdAt(LocalDateTime.now().minusDays(1))
//                    .activatedAt(LocalDateTime.now().minusDays(1))
//                    .build();
//
//            when(queueTokenRepository.findFirstByToken("expiredToken")).thenReturn(expiredToken);
//
//            // When & Then
//            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
//                queueTokenService.getTokenPosition("expiredToken");
//            });
//            assertEquals("만료된 토큰입니다.", exception.getMessage());
//        }
//
//        @Test
//        @DisplayName("성공: 대기 상태의 토큰 사용 시 순서 반환")
//        void 대기_토큰() {
//            // Given
//            QueueToken waitingToken = QueueToken.builder()
//                    .id(3L)
//                    .userId(3L)
//                    .token("waitingToken")
//                    .status(QueueToken.Status.WAITING)
//                    .createdAt(LocalDateTime.now())
//                    .activatedAt(LocalDateTime.now())
//                    .build();
//
//            when(queueTokenRepository.findFirstByToken("waitingToken")).thenReturn(waitingToken);
//            when(queueTokenRepository.findQueueTokenEntitiesByStatus(QueueToken.Status.WAITING))
//                    .thenReturn(List.of(waitingToken)); // 대기 중인 토큰 리스트
//
//            // When
//            QueueTokenPositionResult result = queueTokenService.getTokenPosition("waitingToken");
//
//            // Then
//            assertNotNull(result);
//            assertEquals(1, result.getPosition());
//            assertFalse(result.isAvailable());
//        }
//
//        @Test
//        @DisplayName("성공: 활성 상태의 토큰 사용 시 활성 여부 true 반환")
//        void 활성_토큰() {
//            // Given
//            QueueToken availableToken = QueueToken.builder()
//                    .id(4L)
//                    .userId(4L)
//                    .token("availableToken")
//                    .status(QueueToken.Status.AVAILABLE)
//                    .createdAt(LocalDateTime.now())
//                    .activatedAt(LocalDateTime.now())
//                    .build();
//
//            when(queueTokenRepository.findFirstByToken("availableToken")).thenReturn(availableToken);
//
//            // when
//            QueueTokenPositionResult result = queueTokenService.getTokenPosition("availableToken");
//
//            // then
//            assertNotNull(result);
//            assertNull(result.getPosition());
//            assertTrue(result.isAvailable());
//        }
//    }
//    @Nested
//    @DisplayName("deleteExpiredTokenTest")
//    class deleteExpiredTokenTest {
//        @Test
//        @DisplayName("실패: 유효하지 않은 토큰 사용 시 RuntimeException 에러 발생")
//        void 유효하지_않은_토큰() {
//            // Given
//            when(queueTokenRepository.findFirstByToken("invalidToken")).thenReturn(null);
//
//            // When & Then
//            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
//                queueTokenService.getTokenPosition("invalidToken");
//            });
//            assertEquals("유효하지 않은 토큰입니다.", exception.getMessage());
//        }
//    }
//
//    @Nested
//    @DisplayName("activateTokensTest")
//    class activateTokensTest {
//        @Test
//        @DisplayName("성공: EXPIRED 상태의 토큰 삭제")
//        void 만료_토큰_삭제() {
//            // Given
//            LocalDateTime currentDateTime = LocalDateTime.now();
//            QueueToken expiredToken = QueueToken.builder()
//                    .id(1L)
//                    .status(QueueToken.Status.EXPIRED)
//                    .createdAt(currentDateTime)
//                    .activatedAt(null)
//                    .build();
//
//            List<QueueToken> tokens = List.of(expiredToken);
//
//            when(queueTokenRepository.findQueueTokenEntitiesByCreatedAtBeforeOrActivatedAtBeforeOrStatus(any(), any(), eq(QueueToken.Status.EXPIRED)))
//                    .thenReturn(tokens);
//
//            // When
//            queueTokenService.deleteExpiredToken();
//
//            // Then
//            verify(queueTokenRepository).deleteAll(tokens);
//        }
//
//        @Test
//        @DisplayName("성공: createdAt이 6시간 전인 토큰 삭제")
//        void 여섯_시간_전_생성_토큰_삭제() {
//            LocalDateTime sixHoursAgo = LocalDateTime.now().minusHours(6);
//            QueueToken sixHoursOldToken = QueueToken.builder()
//                    .id(2L)
//                    .status(QueueToken.Status.WAITING)
//                    .createdAt(sixHoursAgo.minusMinutes(1))
//                    .activatedAt(null)
//                    .build();
//            List<QueueToken> tokens = List.of(sixHoursOldToken);
//
//            when(queueTokenRepository.findQueueTokenEntitiesByCreatedAtBeforeOrActivatedAtBeforeOrStatus(any(), any(), eq(QueueToken.Status.EXPIRED)))
//                    .thenReturn(tokens);
//
//            // When
//            queueTokenService.deleteExpiredToken();
//
//            // Then
//            verify(queueTokenRepository).deleteAll(tokens);
//        }
//
//        @Test
//        @DisplayName("성공: activatedAt이 10분 전인 토큰 삭제")
//        void 십분_전_활성화_토큰_삭제() {
//            // Given
//            LocalDateTime tenMinutesAgo = LocalDateTime.now().minusMinutes(10);
//            QueueToken tenMinutesInactiveToken = QueueToken.builder()
//                    .id(3L)
//                    .status(QueueToken.Status.WAITING)
//                    .createdAt(null)
//                    .activatedAt(tenMinutesAgo.minusMinutes(1))
//                    .build();
//            List<QueueToken> tokens = List.of(tenMinutesInactiveToken);
//
//            when(queueTokenRepository.findQueueTokenEntitiesByCreatedAtBeforeOrActivatedAtBeforeOrStatus(any(), any(), eq(QueueToken.Status.EXPIRED)))
//                    .thenReturn(tokens);
//
//            // When
//            queueTokenService.deleteExpiredToken();
//
//            // Then
//            verify(queueTokenRepository).deleteAll(tokens);
//        }
//
//        @Test
//        @DisplayName("성공: 삭제할 토큰이 없으면 삭제하지 않음")
//        void 삭제할_토큰_없음() {
//            // Given
//            when(queueTokenRepository.findQueueTokenEntitiesByCreatedAtBeforeOrActivatedAtBeforeOrStatus(any(), any(), eq(QueueToken.Status.EXPIRED)))
//                    .thenReturn(List.of());
//
//            // When
//            queueTokenService.deleteExpiredToken();
//
//            // Then
//            verify(queueTokenRepository, times(0)).deleteAll(any());
//        }
//
//    }
//
//    @Nested
//    @DisplayName("isValidTokenTest")
//    class isValidTokenTest {
//        @Test
//        @DisplayName("실패: 토큰이 존재하지 않을 경우 false 반환")
//        void 존재하지_않는_토큰() {
//            // Given
//            String token = "invalid_token";
//            when(queueTokenRepository.findFirstByToken(token)).thenReturn(null);
//
//            // When&Then
//            assertFalse(queueTokenService.isValidToken(token));
//        }
//        @Test
//        @DisplayName("실패: 활성 토큰이 아닐 경우 false 반환")
//        void 만료된_토큰() {
//            // Given
//            QueueToken expiredToken = QueueToken.builder()
//                    .id(2L)
//                    .userId(2L)
//                    .token("expiredToken")
//                    .status(QueueToken.Status.EXPIRED)
//                    .createdAt(LocalDateTime.now().minusDays(1))
//                    .activatedAt(null)
//                    .build();
//
//            when(queueTokenRepository.findFirstByToken("expiredToken")).thenReturn(expiredToken);
//
//            // When&Then
//            assertFalse(queueTokenService.isValidToken(expiredToken.getToken()));
//        }
//
//        @Test
//        @DisplayName("성공: 유효한 토큰일 경우 true 반환")
//        void 유효한_토큰() {
//            // Given
//            QueueToken expiredToken = QueueToken.builder()
//                    .id(2L)
//                    .userId(2L)
//                    .token("validToken")
//                    .status(QueueToken.Status.AVAILABLE)
//                    .createdAt(LocalDateTime.now().minusDays(1))
//                    .activatedAt(LocalDateTime.now().minusDays(1))
//                    .build();
//
//            when(queueTokenRepository.findFirstByToken("validToken")).thenReturn(expiredToken);
//
//            // When&Then
//            assertTrue(queueTokenService.isValidToken(expiredToken.getToken()));
//        }
//    }

}
