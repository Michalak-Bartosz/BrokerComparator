package org.message.producer.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.message.model.DebugInfo;
import org.message.model.Report;
import org.message.model.User;
import org.message.producer.util.DebugInfoUtil;
import org.message.producer.util.RandomUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.message.producer.kafka.util.KafkaMessageUtil.getKafkaProducerRecord;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {

    @Value("${spring.kafka.topic.identification-data-topic}")
    private String identificationTopicName;

    private final KafkaTemplate<String, User> kafkaTemplateUserEvent;
    private final KafkaTemplate<String, Report> kafkaTemplateReportEvent;
    private final KafkaTemplate<String, DebugInfo> kafkaTemplateDebugInfoEvent;

    public void sendRecord() {
        User user = RandomUtil.generateUser();
        ProducerRecord<String, User> userKafkaRecord = getKafkaProducerRecord(identificationTopicName, user);

        kafkaTemplateUserEvent.send(userKafkaRecord);
        log.info("User sent -> {}", userKafkaRecord);

        List<ProducerRecord<String, Report>> reportRecordList = user.getReports().stream()
                .map(report -> getKafkaProducerRecord(identificationTopicName, user.getReports().get(0)))
                .toList();

        reportRecordList.forEach(reportProducerRecord -> {
            kafkaTemplateReportEvent.send(reportProducerRecord);
            log.info("Report sent -> {}", reportProducerRecord);
        });

        DebugInfo debugInfo = DebugInfoUtil.generateDebugInfo();
        ProducerRecord<String, DebugInfo> debugInfoKafkaRecord = getKafkaProducerRecord(identificationTopicName, debugInfo);

        kafkaTemplateDebugInfoEvent.send(debugInfoKafkaRecord);
        log.info("DebugInfo sent -> {}", debugInfoKafkaRecord);
    }
}
