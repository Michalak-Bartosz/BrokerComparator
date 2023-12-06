package org.message.producer.util;

import lombok.experimental.UtilityClass;
import org.message.model.DebugInfo;

import java.time.Instant;
import java.util.UUID;

@UtilityClass
public class DebugInfoUtil {

    public static DebugInfo generateDebugInfo(UUID testUUID, double testStatusPercent) {
        UUID debugInfoUUID = UUID.randomUUID();
        return DebugInfo.builder()
                .uuid(debugInfoUUID)
                .testUUID(testUUID)
                .testStatusPercentage(testStatusPercent)
                .producedTimestamp(Instant.now())
                .consumedTimestamp(null)
                .deltaTimestamp(null)
                .countOfProducedMessages(null)
                .countOfReceivedMessages(null)
                .averageCpuUsagePercentProducer(null)
                .averageCpuUsagePercentConsumer(null)
                .averageMemoUsagePercentProducer(null)
                .averageMemoUsagePercentConsumer(null)
                .build();
    }
}
