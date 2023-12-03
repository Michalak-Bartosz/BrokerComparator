package org.message.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Address {
    private UUID uuid;
    private UUID userUuid;
    private String streetName;
    private String number;
    private String city;
    private String country;
}
