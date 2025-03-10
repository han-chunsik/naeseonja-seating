package kr.naeseonja.be.server.common.interceptor;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
