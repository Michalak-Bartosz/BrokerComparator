package org.message.consumer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Address {
    @Id
    private UUID uuid;
    private UUID userUuid;
    private String streetName;
    private String number;
    private String city;
    private String country;
}
