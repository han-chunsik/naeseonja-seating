package kr.hhplus.be.server.app.queue.interfaces.dto.response;

public record TokenPositionResponse(
        String token,
        Long position
) {
}
