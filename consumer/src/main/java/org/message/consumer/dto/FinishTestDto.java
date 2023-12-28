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
public class FinishTestDto {
    private UUID testUUID;
    private Integer numberOfReceivedMessagesProducer;
    private Integer numberOfReceivedMessagesConsumer;
}
