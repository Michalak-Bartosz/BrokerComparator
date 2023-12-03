package org.message.model;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class DebugInfo {
    private UUID uuid;
    private Instant producedTimestamp;
    private Instant consumedTimestamp;
    private Instant deltaTimestamp;
    private Long countOfReceivedMessages;
    private Integer cpuUsagePercentProducer;
    private Integer cpuUsagePercentConsumer;
}
