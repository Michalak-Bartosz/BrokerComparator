package org.message.consumer.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.message.consumer.util.StreamMessageUtil;
import org.message.model.DebugInfo;
import org.message.model.Report;
import org.message.model.User;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    @KafkaListener(topicPartitions = @TopicPartition(topic = "${spring.kafka.topic.identification-data-topic}",
            partitionOffsets = {@PartitionOffset(partition = "0", initialOffset = "0")}),
            groupId = "${spring.kafka.consumer.group-id}")
    public void consumeUsers(@Payload User user,
                             @Header(KafkaHeaders.PARTITION) String partition,
                             @Header("event_produced_time") String producedTime,
                             @Header("record_type") String recordType) {
        StreamMessageUtil.addMessage(user);
        log.debug("User record from partition [ {} ] produced time [ {} ] record type [ {} ] received -> {}", partition, producedTime, recordType, user);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "${spring.kafka.topic.identification-data-topic}",
            partitionOffsets = {@PartitionOffset(partition = "1", initialOffset = "0")}),
            groupId = "${spring.kafka.consumer.group-id}")
    public void consumeReports(@Payload Report report,
                               @Header(KafkaHeaders.PARTITION) String partition,
                               @Header("event_produced_time") String producedTime,
                               @Header("record_type") String recordType) {
        StreamMessageUtil.addMessage(report);
        log.debug("Report record from partition [ {} ] produced time [ {} ] record type [ {} ] received -> {}", partition, producedTime, recordType, report);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "${spring.kafka.topic.identification-data-topic}",
            partitionOffsets = {@PartitionOffset(partition = "2", initialOffset = "0")}),
            groupId = "${spring.kafka.consumer.group-id}")
    public void consumeDebugInfo(@Payload DebugInfo debugInfo,
                                 @Header(KafkaHeaders.PARTITION) String partition,
                                 @Header("event_produced_time") String producedTime,
                                 @Header("record_type") String recordType) {
        StreamMessageUtil.addMessage(debugInfo);
        log.debug("DebugInfo record from partition [ {} ] produced time [ {} ] record type [ {} ] received -> {}", partition, producedTime, recordType, debugInfo);
    }
}
