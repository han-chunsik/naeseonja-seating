package kr.hhplus.be.server.queue.infrastructure.persistence;

import kr.hhplus.be.server.queue.domain.model.QueueToken;
import kr.hhplus.be.server.queue.domain.repository.QueueTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
@Primary
public class QueueTokenRedisRepositoryImpl implements QueueTokenRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String QUEUE_TOKEN_KEY = "queue_token";
    private static final String ACTIVE_TOKEN_KEY = "active_token";

    @Override
    public QueueToken createToken(QueueToken queueToken) {
        ZSetOperations<String, Object> zSetOps = redisTemplate.opsForZSet();
        zSetOps.add(QUEUE_TOKEN_KEY, queueToken.getToken(), Instant.now().getEpochSecond());
        // to-do: 개별 value 만료 구현
        return queueToken;
    }

    @Override
    public boolean isTokenActive(String token) {
        HashOperations<String, Object, Object> hashOps = redisTemplate.opsForHash();
        return Boolean.TRUE.equals(hashOps.hasKey(ACTIVE_TOKEN_KEY, token));
    }

    @Override
    public Optional<Long>  getTokenPosition(String token) {
        ZSetOperations<String, Object> zSetOps = redisTemplate.opsForZSet();
        Long rank = zSetOps.rank(QUEUE_TOKEN_KEY, token);
        return Optional.ofNullable(rank);
    }

    @Override
    public void activateTokens(int activeQueueLimit) {
        // todo 트랜잭션 묶기
        Set<Object> zRange = redisTemplate.opsForZSet().range(QUEUE_TOKEN_KEY, 0, activeQueueLimit);
        if (!zRange.isEmpty()) {
            for (Object item : zRange) {
                long currentTimestamp = System.currentTimeMillis() / 1000;
                redisTemplate.opsForHash().put(ACTIVE_TOKEN_KEY, String.valueOf(item.toString()), String.valueOf(currentTimestamp));
                redisTemplate.opsForZSet().remove(QUEUE_TOKEN_KEY, item);
            }
        }
    }
}
