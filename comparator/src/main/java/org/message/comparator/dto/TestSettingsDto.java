package org.message.comparator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.message.comparator.util.BrokerType;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestSettingsDto {
    private List<BrokerType> brokerTypes;
    private Integer numberOfMessagesToSend;
    private Integer numberOfAttempts;
    private Long delayInMilliseconds;
    private UUID testUUID;
}
