package org.message.consumer.kafka;

import lombok.extern.slf4j.Slf4j;
import org.message.model.Report;
import org.message.model.User;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topicPartitions = @TopicPartition(topic = "${spring.kafka.topic.identification-data-topic}",
            partitionOffsets = {@PartitionOffset(partition = "1", initialOffset = "0")}),
            groupId = "${spring.kafka.consumer.group-id}")
    public void consumeUsers(@Payload User user,
                             @Header(KafkaHeaders.PARTITION) String partition,
                             @Header("produced-time") String producedTime,
                             @Header("record-type") String recordType) {
        log.info("User record from partition [ {} ] produced time [ {} ] record type [ {} ] recieved -> {}", partition, producedTime, recordType, user.toString());
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "${spring.kafka.topic.identification-data-topic}",
            partitionOffsets = {@PartitionOffset(partition = "2", initialOffset = "0")}),
            groupId = "${spring.kafka.consumer.group-id}")
    public void consumeReports(@Payload Report report,
                               @Header(KafkaHeaders.PARTITION) String partition,
                               @Header("produced-time") String producedTime,
                               @Header("record-type") String recordType) {
        log.info("Report record from partition [ {} ] produced time [ {} ] record type [ {} ] recieved -> {}", partition, producedTime, recordType, report.toString());
    }
}
