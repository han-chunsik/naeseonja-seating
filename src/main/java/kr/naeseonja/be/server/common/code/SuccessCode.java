package kr.naeseonja.be.server.common.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {
    SUCCESS(HttpStatus.OK.value(), "SUCCESS");

    private final int code;
    private final String message;
}
