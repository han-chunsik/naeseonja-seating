package kr.naeseonja.be.server.reservation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@Slf4j
public class KafkaConnectionTest {
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    private final CountDownLatch doneSignal = new CountDownLatch(1);

    public List<String> record = new ArrayList<>();

    @KafkaListener(topics = "test", groupId = "concert")
    public void consumer(String message) {
        this.record.add(message);
        doneSignal.countDown();
    }

    @Test
    @DisplayName("Kafka 연동 테스트: 메시지를 보내면, 동일한 메시지를 받는다.")
    public void producer() throws InterruptedException {
        String sendMessage = "데이터 받아라아아~";
        kafkaTemplate.send("test", sendMessage);

        doneSignal.await();

        assertThat(record).isNotEmpty();
        assertThat(sendMessage).isEqualTo(record.get(0));
    }
}