package org.message.producer.exception;

public class NullProducerRecordException extends RuntimeException {

    public NullProducerRecordException() {
        super("Producer record is null!");
    }
}
