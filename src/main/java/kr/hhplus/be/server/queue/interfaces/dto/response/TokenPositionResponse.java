package kr.hhplus.be.server.queue.interfaces.dto.response;

public record TokenPositionResponse(
        String token,
        Long position
) {
}
