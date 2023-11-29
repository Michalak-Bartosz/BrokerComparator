package org.message.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Address {
    private UUID uuid;
    private UUID userUuid;
    private String streetName;
    private String number;
    private String city;
    private String country;
}
