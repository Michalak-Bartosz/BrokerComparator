package org.message.producer.util;

import lombok.experimental.UtilityClass;

import java.nio.charset.StandardCharsets;

@UtilityClass
public class DataSizeUtil {

    public static int getObjectSizeInBytes(Object object) {
        return object.toString().getBytes(StandardCharsets.UTF_8).length;
    }
}
