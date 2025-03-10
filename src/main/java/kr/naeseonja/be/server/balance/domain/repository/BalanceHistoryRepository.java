package kr.naeseonja.be.server.balance.domain.repository;

import kr.naeseonja.be.server.balance.domain.model.BalanceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceHistoryRepository extends JpaRepository<BalanceHistory, Long> {
}
