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

    public Address(String streetName, String number, String city, String country) {
        this.uuid = UUID.randomUUID();
        this.streetName = streetName;
        this.number = number;
        this.city = city;
        this.country = country;
    }
}
