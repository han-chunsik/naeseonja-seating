package kr.hhplus.be.server.app.balance.domain.entity;

import jakarta.persistence.*;

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

    public Balance(Long id, Long userId, Long balance) {
        this.id = id;
        this.userId = userId;
        this.balance = balance;
    }

    public Balance() {
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getBalance() {
        return balance;
    }
}
