package org.message.producer.exception;


import org.message.model.util.BrokerType;

public class UnsupportedBrokerTypeException extends RuntimeException {
    public UnsupportedBrokerTypeException(BrokerType brokerType) {
        super("Unsupported broker type! Provided broker type: " + brokerType);
    }
}
