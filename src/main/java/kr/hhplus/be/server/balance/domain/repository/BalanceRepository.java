package kr.hhplus.be.server.balance.domain.repository;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.balance.domain.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {
    Optional<Balance> findFirstByUserId(Long userId);
}
