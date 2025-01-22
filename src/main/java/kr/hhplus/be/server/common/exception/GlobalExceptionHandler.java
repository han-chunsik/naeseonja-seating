package kr.hhplus.be.server.common.exception;

import kr.hhplus.be.server.balance.exception.BalanceException;
import kr.hhplus.be.server.common.dto.CommonResponse;
import kr.hhplus.be.server.common.interceptor.UnauthorizedException;
import kr.hhplus.be.server.concert.exception.ConcertException;
import kr.hhplus.be.server.queue.exception.QueueException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
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
    @ExceptionHandler(QueueException.class)
    public ResponseEntity<CommonResponse<?>> handleQueueException(QueueException e) {
        log.error("{} {}", e.getClass().getSimpleName(), e.getMessage());
        return ResponseEntity
                .status(e.getErrorCode().getCode())
                .body(CommonResponse.fail(
                        e.getErrorCode().getCode(),
                        e.getMessage()
                ));
    }

    @ExceptionHandler(ConcertException.class)
    public ResponseEntity<CommonResponse<?>> handleConcertException(ConcertException e) {
        log.error("{} {}", e.getClass().getSimpleName(), e.getMessage());
        return ResponseEntity
                .status(e.getErrorCode().getCode())
                .body(CommonResponse.fail(
                        e.getErrorCode().getCode(),
                        e.getMessage()
                ));
    }

    @ExceptionHandler(BalanceException.class)
    public ResponseEntity<CommonResponse<?>> handleBalanceException(BalanceException e) {
        log.error("{} {}", e.getClass().getSimpleName(), e.getMessage());
        return ResponseEntity
                .status(e.getErrorCode().getCode())
                .body(CommonResponse.fail(
                        e.getErrorCode().getCode(),
                        e.getMessage()
                ));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<CommonResponse<?>> handleUnauthorizedException(UnauthorizedException e) {
        log.error("{} {}", e.getClass().getSimpleName(), e.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(CommonResponse.fail(HttpStatus.UNAUTHORIZED.value(), e.getMessage()));
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<CommonResponse<?>> handleObjectOptimisticLockingFailureException(ObjectOptimisticLockingFailureException e) {
        log.error("{} {}", e.getClass().getSimpleName(), e.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(CommonResponse.fail(HttpStatus.CONFLICT.value(), e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<?>> handleMethodArgumentValidationException(MethodArgumentNotValidException e) {
        String errorMessages = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));

        log.error("{} {}", e.getClass().getSimpleName(), errorMessages);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(CommonResponse.fail(HttpStatus.BAD_REQUEST.value(), errorMessages));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<CommonResponse<?>> handleMethodValidationException(HandlerMethodValidationException e) {
        String errorMessages = Arrays.stream(Objects.requireNonNull(e.getDetailMessageArguments()))
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        log.error("{} {}", e.getClass().getSimpleName(), errorMessages);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(CommonResponse.fail(HttpStatus.BAD_REQUEST.value(), errorMessages));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<?>> handleGeneralException(Exception e) {
        log.error("{} {}", e.getClass().getSimpleName(), e.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CommonResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
}
