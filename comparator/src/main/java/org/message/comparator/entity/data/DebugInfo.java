package org.message.comparator.entity.data;

import jakarta.persistence.*;
import lombok.*;
import org.message.comparator.entity.data.metric.CPUMetric;
import org.message.comparator.entity.data.metric.DataSizeMetric;
import org.message.comparator.entity.data.metric.MemoryMetric;
import org.message.comparator.entity.data.util.BrokerType;
import org.message.comparator.util.metric.ReportTimeMetricUtil;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class DebugInfo {
    @Id
    private UUID uuid;
    private UUID testUUID;
    private UUID userUUID;
    private Boolean isSync;
    private Integer numberOfAttempt;
    private Long delayBetweenAttemptsInMilliseconds;
    @Enumerated(EnumType.STRING)
    private BrokerType brokerType;
    @Column(precision = 5, scale = 2)
    private BigDecimal testStatusPercentage;
    private Instant producedTimestamp;
    private Instant consumedTimestamp;
    private Duration deltaTimestamp;
    private String formattedDeltaTimestamp;
    private Integer countOfProducedMessages;
    private Integer countOfConsumedMessages;
    @OneToOne(cascade = CascadeType.ALL)
    private DataSizeMetric dataSizeMetric;
    @OneToOne(cascade = CascadeType.ALL)
    private MemoryMetric producerMemoryMetrics;
    @OneToOne(cascade = CascadeType.ALL)
    private MemoryMetric consumerMemoryMetrics;
    @OneToOne(cascade = CascadeType.ALL)
    private CPUMetric producerCPUMetrics;
    @OneToOne(cascade = CascadeType.ALL)
    private CPUMetric consumerCPUMetrics;
}
