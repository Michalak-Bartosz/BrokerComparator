package org.message.producer.kafka.exception;

public class NullProducerRecordException extends RuntimeException {

    public NullProducerRecordException() {
        super("Producer record is null!");
    }
}
