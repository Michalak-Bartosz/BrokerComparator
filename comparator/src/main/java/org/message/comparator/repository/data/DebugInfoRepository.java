package org.message.comparator.repository.data;

import org.message.comparator.entity.data.DebugInfo;
import org.message.comparator.entity.data.util.BrokerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DebugInfoRepository extends JpaRepository<DebugInfo, UUID> {

    List<DebugInfo> findAllByTestUUID(UUID testUUID);
    List<DebugInfo> findAllByTestUUIDAndBrokerType(UUID testUUID, BrokerType brokerType);
}