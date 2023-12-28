package org.message.consumer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RedirectFinishTestResponseDto {
    private String compareApiResponse;
    private String producerApiResponse;
    private String consumerApiResponse;
    private boolean isProducerFinishTest;
    private boolean isConsumerFinishTest;
    private UUID testUUID;
}
