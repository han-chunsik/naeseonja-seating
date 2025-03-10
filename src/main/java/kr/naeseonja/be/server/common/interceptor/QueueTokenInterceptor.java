package kr.naeseonja.be.server.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.naeseonja.be.server.queue.domain.service.QueueTokenValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class QueueTokenInterceptor implements HandlerInterceptor {
    private final QueueTokenValidationService queueTokenValidationService;

    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER_PREFIX)) {
            throw new UnauthorizedException("유효하지 않은 인증 헤더입니다.");
        }

        String token = authorizationHeader.substring(BEARER_PREFIX.length()).trim();
        // 활성 토큰 유효성 검증
        if (!queueTokenValidationService.isValidToken(token)) {
            throw new UnauthorizedException("유효하지 않은 인증 토큰입니다.");
        }

        return true;
    }
}
