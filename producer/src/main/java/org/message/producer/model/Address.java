package org.message.producer.model;

import lombok.Data;

@Data
public class Address {
    private Long id;
    private String streetName;
    private String number;
    private String city;
    private String country;

    public Address(String streetName, String number, String city, String country) {
        this.streetName = streetName;
        this.number = number;
        this.city = city;
        this.country = country;
    }
}
