package org.message.producer.kafka.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.message.producer.kafka.exception.NullProducerRecordException;
import org.message.producer.kafka.exception.UnsupportedProducerRecordException;
import org.message.producer.model.Report;
import org.message.producer.model.User;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@UtilityClass
public class KafkaMessageUtil {
    private static final Integer USER_TOPIC_PARTITION = 1;
    private static final Integer RECORD_TOPIC_PARTITION = 2;
    private static final Integer DEBUG_TOPIC_PARTITION = 3;

    public static <T> ProducerRecord<String, T> getKafkaProducerRecord(String topic, T object) {
        return switch (object) {
            case User u ->
                    new ProducerRecord<>(topic, USER_TOPIC_PARTITION, generateKafkaKey(u.getUuid(), User.class.getSimpleName()), (T) u, generateHeaders(User.class.getSimpleName()));
            case Report r ->
                    new ProducerRecord<>(topic, RECORD_TOPIC_PARTITION, generateKafkaKey(r.getUserUuid(), Report.class.getSimpleName()), (T) r, generateHeaders(Report.class.getSimpleName()));
            case null -> throw new NullProducerRecordException();
            default -> throw new UnsupportedProducerRecordException();
        };
    }

    private static String generateKafkaKey(UUID uuid, String className) {
        return StringUtils.lowerCase("kafka-" + className + "-key-" + uuid);
    }

    private static List<Header> generateHeaders(String className) {
        return List.of(new RecordHeader("produce-time", Instant.now().toString().getBytes(StandardCharsets.UTF_8)),
                new RecordHeader("record-type", className.getBytes(StandardCharsets.UTF_8)));
    }
}
