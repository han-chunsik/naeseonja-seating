package kr.hhplus.be.server.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class CommonResponseDTO<T> {
    private int code;
    private String message;
    private T data;

    @Builder
    public CommonResponseDTO(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
