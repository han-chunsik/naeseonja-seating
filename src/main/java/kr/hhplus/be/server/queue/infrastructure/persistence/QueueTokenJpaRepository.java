package kr.hhplus.be.server.queue.infrastructure.persistence;

import kr.hhplus.be.server.queue.domain.entity.QueueToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueueTokenJpaRepository extends JpaRepository<QueueToken, Long> {
    QueueToken findFirstByUserId(Long userId);
}
