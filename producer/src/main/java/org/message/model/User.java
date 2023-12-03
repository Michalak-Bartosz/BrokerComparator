package org.message.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class User {
    private UUID uuid;
    private String idNumber;
    private String name;
    private String lastName;
    private String email;
    private String cellPhone;
    private Address address;
    private List<Report> reports;
}
