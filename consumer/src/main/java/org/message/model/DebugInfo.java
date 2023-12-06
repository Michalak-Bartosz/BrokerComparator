package org.message.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DebugInfo {
    private UUID uuid;
    private UUID testUUID;
    private double testStatusPercentage;
    private Instant producedTimestamp;
    private Instant consumedTimestamp;
    private Instant deltaTimestamp;
    private Long countOfProducedMessages;
    private Long countOfReceivedMessages;
    private Integer averageCpuUsagePercentProducer;
    private Integer averageCpuUsagePercentConsumer;
    private Integer averageMemoUsagePercentProducer;
    private Integer averageMemoUsagePercentConsumer;
}
