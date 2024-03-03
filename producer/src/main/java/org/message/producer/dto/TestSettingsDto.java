package org.message.producer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.message.model.util.BrokerType;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestSettingsDto {
    @JsonProperty
    private boolean isSync;
    private List<BrokerType> brokerTypes;
    private Integer numberOfMessagesToSend;
    private Integer numberOfAttempts;
    private Long delayInMilliseconds;
    private UUID testUUID;
}
