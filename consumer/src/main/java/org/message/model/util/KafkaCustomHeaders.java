package org.message.model.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class KafkaCustomHeaders {

    public static final String RECORD_TYPE = "record_type";
    public static final String EVENT_PRODUCED_TIME = "event_produced_time";
}
