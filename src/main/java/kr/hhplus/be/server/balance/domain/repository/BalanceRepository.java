package kr.hhplus.be.server.balance.domain.repository;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.balance.domain.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT b FROM Balance b WHERE b.userId = :userId")
    Balance findFirstByUserIdWithLock(@Param("userId") Long userId);
    Balance findFirstByUserId(Long userId);
}
