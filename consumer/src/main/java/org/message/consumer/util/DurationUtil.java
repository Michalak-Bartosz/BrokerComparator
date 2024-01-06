package org.message.consumer.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.time.DurationFormatUtils;

import java.time.Duration;

@UtilityClass
public class DurationUtil {
    public static String humanReadableDuration(Duration duration) {
        return DurationFormatUtils.formatDuration(duration.toMillis(), "HH'h' mm'min' ss's' SSS'ms'", false);
    }
}
