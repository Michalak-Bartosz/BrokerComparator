package org.message.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.message.model.metric.CPUMetric;
import org.message.model.metric.MemoryMetric;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DebugInfo {
    private UUID uuid;
    private UUID testUUID;
    private Integer numberOfAttempt;
    private String brokerType;
    private BigDecimal testStatusPercentage;
    private Instant producedTimestamp;
    private Instant consumedTimestamp;
    private Duration deltaTimestamp;
    private Integer countOfProducedMessages;
    private Integer countOfConsumedMessages;
    private MemoryMetric producerMemoryMetrics;
    private MemoryMetric consumerMemoryMetrics;
    private CPUMetric producerCPUMetrics;
    private CPUMetric consumerCPUMetrics;
}
