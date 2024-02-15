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

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import static org.message.producer.service.TestService.getCurrentBrokerStatusPercentage;
import static org.message.producer.service.TestService.getCurrentTestStatusPercentage;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DebugInfo {
    private UUID uuid;
    private UUID testUUID;
    private UUID userUUID;
    private Integer numberOfAttempt;
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
            Integer numberOfAttempt,
            BrokerType brokerType,
            Integer messagesObtained,
            Integer payloadSizeInBytes,
            Integer producedDataInTestInBytes,
            BigDecimal systemAverageCpu,
            BigDecimal appAverageCpu) {
        this.uuid = UUID.randomUUID();
        this.testUUID = testUUID;
        this.userUUID = userUUID;
        this.numberOfAttempt = numberOfAttempt;
        this.brokerType = brokerType;
        this.testStatusPercentage = getCurrentTestStatusPercentage();
        this.brokerStatusPercentage = getCurrentBrokerStatusPercentage();
        this.producedTimestamp = Instant.now();
        this.consumedTimestamp = null;
        this.deltaTimestamp = null;
        this.countOfProducedMessages = messagesObtained;
        this.countOfConsumedMessages = null;
        this.dataSizeMetric = MetricUtil.getDataSizeMetric(payloadSizeInBytes, producedDataInTestInBytes, brokerType);
        this.producerMemoryMetrics = MetricUtil.getMemoryMetrics(brokerType);
        this.consumerMemoryMetrics = null;
        this.producerCPUMetrics = MetricUtil.getCpuMetrics(systemAverageCpu, appAverageCpu, brokerType);
        this.consumerCPUMetrics = null;
    }
}
