package org.message.comparator.service.data;

import lombok.RequiredArgsConstructor;
import org.message.comparator.entity.data.User;
import org.message.comparator.entity.data.util.BrokerType;
import org.message.comparator.repository.data.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsersByTestUUID(UUID testUUID) {
        return userRepository.findAllByTestUUID(testUUID);
    }

    public List<User> getAllUsersByUUIDAndBrokerType(UUID testUUID, BrokerType brokerType) {
        return userRepository.findAllByTestUUIDAndBrokerType(testUUID, brokerType);
    }
}
