package kr.hhplus.be.server.queue.exception;

import lombok.Getter;

@Getter
public class QueueException  extends RuntimeException{
    private final QueueErrorCode errorCode;

    public QueueException(QueueErrorCode errorCode, Object... args) {
        super(errorCode.getMessageWithArgs(args));
        this.errorCode = errorCode;
    }
}
