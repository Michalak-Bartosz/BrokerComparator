package org.message.consumer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class User {
    @Id
    private UUID uuid;
    private UUID testUUID;
    private String idNumber;
    private String name;
    private String lastName;
    private String email;
    private String cellPhone;
    @OneToOne
    private Address address;
    @OneToMany
    private List<Report> reports;
}
