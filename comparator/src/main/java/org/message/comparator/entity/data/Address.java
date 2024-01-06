package org.message.comparator.entity.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.*;
import org.message.comparator.entity.data.id.AddressIdKey;
import org.message.comparator.entity.data.util.BrokerType;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@IdClass(AddressIdKey.class)
public class Address {
    @Id
    private UUID uuid;
    @Id
    private UUID userUuid;
    @Id
    private BrokerType brokerType;

    private String streetName;
    private String number;
    private String city;
    private String country;
}
