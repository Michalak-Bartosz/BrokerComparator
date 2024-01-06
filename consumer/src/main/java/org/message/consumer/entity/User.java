package org.message.consumer.entity;

import jakarta.persistence.*;
import lombok.*;
import org.message.consumer.entity.id.UserIdKey;
import org.message.model.util.BrokerType;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@IdClass(UserIdKey.class)
public class User {
    @Id
    private UUID uuid;
    @Id
    private UUID testUUID;
    @Id
    @Enumerated(EnumType.STRING)
    private BrokerType brokerType;

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
