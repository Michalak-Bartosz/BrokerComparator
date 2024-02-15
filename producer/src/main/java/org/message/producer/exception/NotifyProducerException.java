package org.message.producer.exception;

import java.math.BigDecimal;

public class NotifyProducerException extends RuntimeException {

    public NotifyProducerException(BigDecimal brokerStatusPercentage) {
        super("Notify producer exception! Current broker status percentage:" + brokerStatusPercentage);
    }
}
