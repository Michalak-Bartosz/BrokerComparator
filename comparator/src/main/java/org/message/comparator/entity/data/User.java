package org.message.comparator.entity.data;

import jakarta.persistence.*;
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
    @OneToMany(fetch = FetchType.EAGER)
    private List<Report> reports;
}
