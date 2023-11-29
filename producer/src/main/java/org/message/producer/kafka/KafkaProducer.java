package org.message.producer.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.message.producer.model.User;
import org.message.producer.util.RandomUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static org.message.producer.kafka.util.KafkaMessageUtil.getKafkaProducerRecord;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {

    @Value("${spring.kafka.topic.identification-data-topic}")
    private String identificationTopicName;

    private final KafkaTemplate<String, User> kafkaTemplateUserEvent;

    public void sendMessage() {
        User user = RandomUtil.generateUser();
        ProducerRecord<String, User> kafkaRecord = getKafkaProducerRecord(identificationTopicName, user);

        log.info("Message sent -> {}", kafkaRecord);

        kafkaTemplateUserEvent.send(kafkaRecord);
    }
}
