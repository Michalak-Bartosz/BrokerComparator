package org.message.comparator.entity.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.message.comparator.entity.data.metric.CPUMetric;
import org.message.comparator.entity.data.metric.MemoryMetric;

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
    private Integer numberOfAttempt;
    private String brokerType;
    @Column(precision=5, scale=2)
    private BigDecimal testStatusPercentage;
    private Instant producedTimestamp;
    private Instant consumedTimestamp;
    private Duration deltaTimestamp;
    private Integer countOfProducedMessages;
    private Integer countOfConsumedMessages;
    @OneToOne
    private MemoryMetric producerMemoryMetrics;
    @OneToOne
    private MemoryMetric consumerMemoryMetrics;
    @OneToOne
    private CPUMetric producerCPUMetrics;
    @OneToOne
    private CPUMetric consumerCPUMetrics;
}
