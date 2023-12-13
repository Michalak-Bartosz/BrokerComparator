package org.message.comparator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.message.comparator.util.BrokerType;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestSettingsDto {
    private BrokerType brokerType;
    private Integer numberOfMessagesToSend;
    private UUID testUUID;
}
