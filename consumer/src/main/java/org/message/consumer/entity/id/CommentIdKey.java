package org.message.consumer.entity.id;

import org.message.model.util.BrokerType;

import java.io.Serializable;
import java.util.UUID;

public class CommentIdKey implements Serializable {
    private UUID uuid;
    private UUID reportUuid;
    private BrokerType brokerType;
}
