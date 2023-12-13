package org.message.producer.util;

import lombok.experimental.UtilityClass;
import org.message.model.DebugInfo;
import org.message.model.metric.CPUMetric;
import org.message.model.metric.MemoryMetric;

import java.time.Instant;
import java.util.UUID;

import static org.message.producer.util.MetricUtil.getCpuMetrics;
import static org.message.producer.util.MetricUtil.getMemoryMetrics;

@UtilityClass
public class DebugInfoUtil {

    public static DebugInfo generateDebugInfo(
            UUID testUUID,
            double testStatusPercent,
            Integer messagesObtained,
            double systemAverageCpu,
            double appAverageCpu) {
        final UUID debugInfoUUID = UUID.randomUUID();
        final MemoryMetric producerMemoryMetric = getMemoryMetrics();
        final CPUMetric producerCPUMetric = getCpuMetrics(systemAverageCpu, appAverageCpu);
        return DebugInfo.builder()
                .uuid(debugInfoUUID)
                .testUUID(testUUID)
                .testStatusPercentage(testStatusPercent)
                .producedTimestamp(Instant.now())
                .consumedTimestamp(null)
                .deltaTimestamp(null)
                .countOfProducedMessages(messagesObtained)
                .countOfConsumedMessages(null)
                .producerMemoryMetrics(producerMemoryMetric)
                .consumerMemoryMetrics(null)
                .producerCPUMetrics(producerCPUMetric)
                .consumerCPUMetrics(null)
                .build();
    }
}
