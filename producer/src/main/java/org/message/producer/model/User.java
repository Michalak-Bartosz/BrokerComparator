package org.message.producer.model;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class User {
    private UUID uuid;
    private String idNumber;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private Address address;
    private List<Report> reports;

    public User(String idNumber, String name, String surname, String email, String phone, Address address, List<Report> reports) {
        this.uuid = UUID.randomUUID();
        this.idNumber = idNumber;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        address.setUserUuid(uuid);
        this.address = address;
        reports.forEach(report -> report.setUserUuid(uuid));
        this.reports = reports;
    }
}
