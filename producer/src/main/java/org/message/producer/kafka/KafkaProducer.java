package org.message.producer.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.message.model.Report;
import org.message.model.User;
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
    private final KafkaTemplate<String, Report> kafkaTemplateReportEvent;

    public void sendUserRecord() {
        User user = RandomUtil.generateUser();
        ProducerRecord<String, User> kafkaRecord = getKafkaProducerRecord(identificationTopicName, user);

        log.info("User sent -> {}", kafkaRecord);
        kafkaTemplateUserEvent.send(kafkaRecord);
    }

    public void sendReportRecord() {
        User user = RandomUtil.generateUser();
        ProducerRecord<String, Report> kafkaRecord = getKafkaProducerRecord(identificationTopicName, user.getReports().get(0));

        log.info("Report sent -> {}", kafkaRecord);
        kafkaTemplateReportEvent.send(kafkaRecord);
    }
}
