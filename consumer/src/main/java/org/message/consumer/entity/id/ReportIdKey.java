package org.message.consumer.entity.id;

import org.message.model.util.BrokerType;

import java.io.Serializable;
import java.util.UUID;

public class ReportIdKey implements Serializable {
    private UUID uuid;
    private UUID testUUID;
    private UUID userUuid;
    private BrokerType brokerType;
}
