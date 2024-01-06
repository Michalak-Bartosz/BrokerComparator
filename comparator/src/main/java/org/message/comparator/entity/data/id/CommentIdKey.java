package org.message.comparator.entity.data.id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.message.comparator.entity.data.util.BrokerType;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentIdKey implements Serializable {
    private UUID uuid;
    private UUID reportUuid;
    private BrokerType brokerType;
}
