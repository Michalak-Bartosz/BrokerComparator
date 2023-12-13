package org.message.producer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.message.producer.util.BrokerType;

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
