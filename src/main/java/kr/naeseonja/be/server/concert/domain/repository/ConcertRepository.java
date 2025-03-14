package kr.naeseonja.be.server.concert.domain.repository;

import kr.naeseonja.be.server.concert.domain.model.Concert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConcertRepository extends JpaRepository<Concert, Long> {
    Optional<Concert> findById(Long id);
}
