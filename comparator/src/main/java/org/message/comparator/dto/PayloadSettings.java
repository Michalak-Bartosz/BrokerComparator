package org.message.comparator.dto;

import lombok.Data;
import org.message.comparator.util.BrokerType;

@Data
public class PayloadSettings {
    private BrokerType brokerType;
    private Long numberOfUsers;
}
