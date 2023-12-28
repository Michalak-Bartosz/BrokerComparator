package org.message.comparator.config;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApiConstants {
    public static final String REQUEST_MAPPING_NAME = "/api/v1/compare";
    public static final String REQUEST_MAPPING_NAME_PREFIX = "/api/v1/compare/**";
    public static final String PRODUCER_API_URL_ADDRESS = "http://localhost:8080/api/v1/producer/test";
}
