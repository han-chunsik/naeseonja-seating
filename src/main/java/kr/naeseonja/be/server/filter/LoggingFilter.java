package kr.naeseonja.be.server.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Slf4j
@Component
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();

        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            long latency = System.currentTimeMillis() - startTime;

            String requestBody = new String(wrappedRequest.getContentAsByteArray(), request.getCharacterEncoding());
            String responseBody = new String(wrappedResponse.getContentAsByteArray(), response.getCharacterEncoding());

            String logMsg =  String.format("Request: method=%s, uri=%s, body=%s, Response: status=%s, body=%s, latency=%sms",
                    request.getMethod(),
                    request.getRequestURI(),
                    requestBody,
                    response.getStatus(),
                    responseBody,
                    latency);

            logger.info(logMsg);

            // Response Content 복사
            wrappedResponse.copyBodyToResponse();
        }
    }
}
