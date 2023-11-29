package org.message.consumer.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumer {
    @KafkaListener(topics = "${spring.kafka.topic.identification-data-topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String userString) {
        log.info("Json message recieved -> {}", userString);
    }
}
