package org.message.consumer.mappers;

import lombok.experimental.UtilityClass;
import org.message.consumer.entity.Address;

@UtilityClass
public class AddressMapper {

    public static Address mapAddressModelToEntity(org.message.model.Address addressModel) {
        return Address.builder()
                .uuid(addressModel.getUuid())
                .userUuid(addressModel.getUserUuid())
                .brokerType(addressModel.getBrokerType())
                .streetName(addressModel.getStreetName())
                .number(addressModel.getNumber())
                .city(addressModel.getCity())
                .country(addressModel.getCountry())
                .build();
    }
}
