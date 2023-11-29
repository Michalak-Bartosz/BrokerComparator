package org.message.model;

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
}
