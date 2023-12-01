package org.message.consumer.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.message.model.Report;
import org.message.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Slf4j
public class KafkaConsumer {

    @Autowired
    private SimpMessagingTemplate socketTemplate;

    @KafkaListener(topicPartitions = @TopicPartition(topic = "${spring.kafka.topic.identification-data-topic}",
            partitionOffsets = {@PartitionOffset(partition = "1", initialOffset = "0")}),
            groupId = "${spring.kafka.consumer.group-id}")
    public void consumeUsers(@Payload User user,
                             @Header(KafkaHeaders.PARTITION) String partition,
                             @Header("event_produced_time") String producedTime,
                             @Header("record_type") String recordType) {
        log.info("User record from partition [ {} ] produced time [ {} ] record type [ {} ] recieved -> {}", partition, producedTime, recordType, user.toString());
        socketTemplate.convertAndSend("/topic/user-group", user);
        log.info("Send to ws /topic/user-group");
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "${spring.kafka.topic.identification-data-topic}",
            partitionOffsets = {@PartitionOffset(partition = "2", initialOffset = "0")}),
            groupId = "${spring.kafka.consumer.group-id}")
    public void consumeReports(@Payload Report report,
                               @Header(KafkaHeaders.PARTITION) String partition,
                               @Header("event_produced_time") String producedTime,
                               @Header("record_type") String recordType) {
        log.info("Report record from partition [ {} ] produced time [ {} ] record type [ {} ] recieved -> {}", partition, producedTime, recordType, report.toString());
        socketTemplate.convertAndSend("/topic/report-group", report);
        log.info("Send to ws /topic/report-group");
    }
}
