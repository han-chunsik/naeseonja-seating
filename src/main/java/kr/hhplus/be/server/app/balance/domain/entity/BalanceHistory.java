package kr.hhplus.be.server.app.balance.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

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

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public BalanceHistory(Long id, Long balanceId, Long amount, Type type, LocalDateTime createAt) {
        this.id = id;
        this.balanceId = balanceId;
        this.amount = amount;
        this.type = type;
        this.createdAt = createdAt;
    }

    public BalanceHistory() {
    }

    public Long getId() {
        return id;
    }

    public Long getBalanceId() {
        return balanceId;
    }

    public Long getAmount() {
        return amount;
    }

    public Type getType() {
        return type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
