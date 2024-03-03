package org.message.producer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.message.model.util.BrokerType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinishAsyncTestDto {
    private UUID testUUID;
    private Integer totalNumberOfMessagesInTest;
    private Map<BrokerType, Integer> brokerTotalNumberOfMessagesMap;
}
