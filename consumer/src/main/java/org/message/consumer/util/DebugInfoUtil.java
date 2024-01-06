package org.message.consumer.util;

import lombok.experimental.UtilityClass;
import org.message.consumer.util.metric.MetricUtil;
import org.message.model.DebugInfo;
import org.message.model.metric.CPUMetric;
import org.message.model.metric.MemoryMetric;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;

@UtilityClass
public class DebugInfoUtil {

    public static void updateDebugInfo(
            DebugInfo debugInfo,
            BigDecimal systemAverageCpu,
            BigDecimal appAverageCpu) {
        Instant consumedTimestamp = Instant.now();
        Duration deltaTimestamp = Duration.between(debugInfo.getProducedTimestamp(), consumedTimestamp);
        final MemoryMetric consumerMemoryMetric = MetricUtil.getMemoryMetrics();
        final CPUMetric consumerCPUMetric = MetricUtil.getCpuMetrics(systemAverageCpu, appAverageCpu);

        debugInfo.setCountOfConsumedMessages(debugInfo.getCountOfProducedMessages());
        debugInfo.setConsumedTimestamp(consumedTimestamp);
        debugInfo.setDeltaTimestamp(deltaTimestamp);
        debugInfo.setConsumerMemoryMetrics(consumerMemoryMetric);
        debugInfo.setConsumerCPUMetrics(consumerCPUMetric);
        debugInfo.getDataSizeMetric().setConsumedDataSizeInBytes(debugInfo.getDataSizeMetric().getProducedDataSizeInBytes());
    }
}
