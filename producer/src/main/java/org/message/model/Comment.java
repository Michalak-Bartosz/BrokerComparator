package org.message.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.message.model.util.BrokerType;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private UUID uuid;
    private UUID reportUuid;
    private BrokerType brokerType;
    private String description;
}
