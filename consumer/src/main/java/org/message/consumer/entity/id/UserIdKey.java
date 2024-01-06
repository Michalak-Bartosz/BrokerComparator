package org.message.consumer.entity.id;

import org.message.model.util.BrokerType;

import java.io.Serializable;
import java.util.UUID;

public class UserIdKey implements Serializable {
    private UUID uuid;
    private UUID testUUID;
    private BrokerType brokerType;
}
