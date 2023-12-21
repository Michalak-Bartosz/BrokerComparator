package org.message.producer.kafka.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.message.model.DebugInfo;
import org.message.model.Report;
import org.message.model.User;
import org.message.model.util.KafkaCustomHeaders;
import org.message.producer.kafka.exception.NullProducerRecordException;
import org.message.producer.kafka.exception.UnsupportedProducerRecordException;
import org.springframework.kafka.support.KafkaHeaders;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;

@UtilityClass
public class KafkaMessageUtil {
    private static final Integer USER_TOPIC_PARTITION = 0;
    private static final Integer REPORT_TOPIC_PARTITION = 0;
    private static final Integer DEBUG_INFO_TOPIC_PARTITION = 0;

    public static <T> ProducerRecord<String, T> getKafkaProducerRecord(String topic, T object) {
        return switch (object) {
            case User u ->
                    new ProducerRecord<>(topic, USER_TOPIC_PARTITION, generateKafkaKey(User.class.getSimpleName()), (T) u, generateHeaders(User.class.getSimpleName(), USER_TOPIC_PARTITION));
            case Report r ->
                    new ProducerRecord<>(topic, REPORT_TOPIC_PARTITION, generateKafkaKey(Report.class.getSimpleName()), (T) r, generateHeaders(Report.class.getSimpleName(), REPORT_TOPIC_PARTITION));
            case DebugInfo d ->
                    new ProducerRecord<>(topic, DEBUG_INFO_TOPIC_PARTITION, generateKafkaKey(DebugInfo.class.getSimpleName()), (T) d, generateHeaders(DebugInfo.class.getSimpleName(), DEBUG_INFO_TOPIC_PARTITION));
            case null -> throw new NullProducerRecordException();
            default -> throw new UnsupportedProducerRecordException();
        };
    }

    private static String generateKafkaKey(String className) {
        return StringUtils.lowerCase("kafka-" + className + "-key");
    }

    private static List<Header> generateHeaders(String className, Integer partition) {
        return List.of(new RecordHeader(KafkaHeaders.PARTITION, partition.toString().getBytes(StandardCharsets.UTF_8)),
                new RecordHeader(KafkaCustomHeaders.EVENT_PRODUCED_TIME, Instant.now().toString().getBytes(StandardCharsets.UTF_8)),
                new RecordHeader(KafkaCustomHeaders.RECORD_TYPE, className.getBytes(StandardCharsets.UTF_8)));
    }


}
