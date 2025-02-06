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
            // Given
            // When
            // Then
        }
    }
}
