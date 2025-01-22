package kr.hhplus.be.server.balance.domain.model;

import jakarta.persistence.*;
import kr.hhplus.be.server.balance.exception.BalanceErrorCode;
import kr.hhplus.be.server.balance.exception.BalanceException;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "balance")
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id", unique = true, nullable = false)
    private Long userId;

    @Column(name = "balance", nullable = false)
    private Long balance;

    @Version
    private Long version;

    public Balance(Long id, Long userId, Long balance) {
        this.id = id;
        this.userId = userId;
        this.balance = balance;
    }

    @Getter
    public enum BalanceLimit {
        MAX(100000L),
        MIN(100L);

        private final long value;

        BalanceLimit(long value) {
            this.value = value;
        }
    }

    public Balance charge(long amount) {
        if (amount < BalanceLimit.MIN.getValue()) {
            throw new BalanceException(BalanceErrorCode.INVALID_AMOUNT, amount);
        }

        long newBalance = balance + amount;
        if (newBalance > BalanceLimit.MAX.getValue()) {
            throw new BalanceException(BalanceErrorCode.EXCEEDS_MAX_BALANCE, newBalance);
        }

        this.balance += amount;
        return this;
    }

    public Balance use(long amount) {
        if (amount > balance) {
            throw new BalanceException(BalanceErrorCode.INSUFFICIENT_BALANCE, amount);
        }

        this.balance -= amount;
        return this;
    }
}
