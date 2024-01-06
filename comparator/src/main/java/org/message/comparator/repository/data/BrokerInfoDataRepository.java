package org.message.comparator.repository.data;

import org.message.comparator.entity.data.BrokerInfoData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrokerInfoDataRepository extends JpaRepository<BrokerInfoData, Long> {
}
