package org.message.consumer.entity.id;

import org.message.model.util.BrokerType;

import java.io.Serializable;
import java.util.UUID;

public class AddressIdKey implements Serializable {
    private UUID uuid;
    private UUID userUuid;
    private BrokerType brokerType;
}
