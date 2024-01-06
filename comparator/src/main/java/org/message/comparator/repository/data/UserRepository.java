package org.message.comparator.repository.data;

import org.message.comparator.entity.data.User;
import org.message.comparator.entity.data.util.BrokerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findAllByTestUUID(UUID testUUID);
    List<User> findAllByTestUUIDAndBrokerType(UUID testUUID, BrokerType brokerType);
}
