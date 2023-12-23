package org.message.consumer.service;

import lombok.RequiredArgsConstructor;
import org.message.consumer.entity.Address;
import org.message.consumer.mappers.AddressMapper;
import org.message.consumer.repository.AddressRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public Address saveAddressModel(org.message.model.Address addressModel) {
        Address address = AddressMapper.mapAddressModelToEntity(addressModel);
        addressRepository.save(address);
        addressRepository.flush();
        return address;
    }
}
