package kr.hhplus.be.server.balance.domain.service;

public enum BalanceLimit {
    BALANCE_RECHARGE_LIMIT_MIN(100L),  // 최소값
    BALANCE_LIMIT_MAX(10000000L);  // 최대값

    private final long limit;

    BalanceLimit(long limit) {
        this.limit = limit;
    }

    public long getLimit() {
        return limit;
    }
}
