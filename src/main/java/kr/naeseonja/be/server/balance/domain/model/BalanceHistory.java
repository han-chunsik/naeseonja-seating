package kr.naeseonja.be.server.balance.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Entity
@Table(name = "balance_history")
public class BalanceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "balance_id", nullable = false)
    private Long balanceId;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private Type type;

    public enum Type {
        CHARGE, USE
    }

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public BalanceHistory(Long balanceId, Long amount, Type type) {
        this.balanceId = balanceId;
        this.amount = amount;
        this.type = type;
    }

    public static BalanceHistory createChargeBalanceHistory(Balance userBalance, long amount) {
        return new BalanceHistory(userBalance.getId(), amount, Type.CHARGE);
    }

    public static BalanceHistory createUseBalanceHistory(Balance userBalance, long amount) {
        return new BalanceHistory(userBalance.getId(), amount, Type.USE);
    }
}
