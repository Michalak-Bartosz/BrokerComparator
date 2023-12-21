package org.message.producer.exception;

import org.message.producer.util.BrokerType;

public class UnsupportedBrokerTypeException extends RuntimeException {
    public UnsupportedBrokerTypeException(BrokerType brokerType) {
        super("Unsupported broker type! Provided broker type: " + brokerType);
    }
}
