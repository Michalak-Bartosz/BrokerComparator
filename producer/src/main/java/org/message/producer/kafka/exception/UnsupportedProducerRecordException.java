package org.message.producer.kafka.exception;

public class UnsupportedProducerRecordException extends RuntimeException {

    public UnsupportedProducerRecordException() {
        super("Unsupported producer record exception!");
    }
}
