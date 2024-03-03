package org.message.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.message.model.metric.CPUMetric;
import org.message.model.metric.DataSizeMetric;
import org.message.model.metric.MemoryMetric;
import org.message.model.util.BrokerType;
import org.message.producer.util.MetricUtil;
import org.message.producer.util.TestProgressUtil;

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
    private UUID userUUID;
    private Boolean isSync;
    private Integer numberOfAttempt;
    private Long delayBetweenAttemptsInMilliseconds;
    private BrokerType brokerType;
    private BigDecimal testStatusPercentage;
    private BigDecimal brokerStatusPercentage;
    private Instant producedTimestamp;
    private Instant consumedTimestamp;
    private Duration deltaTimestamp;
    private Integer countOfProducedMessages;
    private Integer countOfConsumedMessages;
    private DataSizeMetric dataSizeMetric;
    private MemoryMetric producerMemoryMetrics;
    private MemoryMetric consumerMemoryMetrics;
    private CPUMetric producerCPUMetrics;
    private CPUMetric consumerCPUMetrics;

    public DebugInfo(
            UUID testUUID,
            UUID userUUID,
            Boolean isSync,
            Integer numberOfAttempt,
            Long delayBetweenAttemptsInMilliseconds,
            BrokerType brokerType,
            User user,
            BigDecimal systemAverageCpu,
            BigDecimal appAverageCpu) {
        this.uuid = UUID.randomUUID();
        this.testUUID = testUUID;
        this.userUUID = userUUID;
        this.isSync = isSync;
        this.numberOfAttempt = numberOfAttempt;
        this.delayBetweenAttemptsInMilliseconds = delayBetweenAttemptsInMilliseconds;
        this.brokerType = brokerType;
        this.testStatusPercentage = TestProgressUtil.getCurrentTestStatusPercentage();
        this.brokerStatusPercentage = TestProgressUtil.getCurrentBrokerStatusPercentage();
        this.producedTimestamp = Instant.now();
        this.consumedTimestamp = null;
        this.deltaTimestamp = null;
        this.countOfProducedMessages = TestProgressUtil.incrementMessageObtainedInAttempt();
        this.countOfConsumedMessages = null;
        this.dataSizeMetric = MetricUtil.getDataSizeMetric(user, brokerType);
        this.producerMemoryMetrics = MetricUtil.getMemoryMetrics(brokerType);
        this.consumerMemoryMetrics = null;
        this.producerCPUMetrics = MetricUtil.getCpuMetrics(systemAverageCpu, appAverageCpu, brokerType);
        this.consumerCPUMetrics = null;
    }
}
