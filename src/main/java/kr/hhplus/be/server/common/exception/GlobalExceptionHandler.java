package kr.hhplus.be.server.common.exception;

import kr.hhplus.be.server.balance.exception.BalanceException;
import kr.hhplus.be.server.common.dto.CommonResponse;
import kr.hhplus.be.server.common.interceptor.UnauthorizedException;
import kr.hhplus.be.server.concert.exception.ConcertException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConcertException.class)
    public ResponseEntity<CommonResponse<?>> handleConcertException(ConcertException e) {
        return ResponseEntity
                .status(e.getErrorCode().getCode())
                .body(CommonResponse.fail(
                        e.getErrorCode().getCode(),
                        e.getMessage()
                ));
    }

    @ExceptionHandler(BalanceException.class)
    public ResponseEntity<CommonResponse<?>> handleBalanceException(BalanceException e) {
        return ResponseEntity
                .status(e.getErrorCode().getCode())
                .body(CommonResponse.fail(
                        e.getErrorCode().getCode(),
                        e.getMessage()
                ));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<CommonResponse<?>> handleUnauthorizedException(UnauthorizedException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(CommonResponse.fail(HttpStatus.UNAUTHORIZED.value(), e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<?>> handleMethodArgumentValidationException(MethodArgumentNotValidException e) {
        String errorMessages = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(CommonResponse.fail(HttpStatus.BAD_REQUEST.value(), errorMessages));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<CommonResponse<?>> handleMethodValidationException(HandlerMethodValidationException e) {
        String messages = Arrays.stream(Objects.requireNonNull(e.getDetailMessageArguments()))
                .map(Object::toString)
                .collect(Collectors.joining(", "));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(CommonResponse.fail(HttpStatus.BAD_REQUEST.value(), messages));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<?>> handleGeneralException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CommonResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
}
