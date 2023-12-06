package org.message.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private UUID uuid;
    private UUID testUUID;
    private String idNumber;
    private String name;
    private String lastName;
    private String email;
    private String cellPhone;
    private Address address;
    private List<Report> reports;
}
