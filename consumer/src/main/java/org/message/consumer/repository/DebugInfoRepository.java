package org.message.consumer.repository;

import org.message.consumer.entity.DebugInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DebugInfoRepository extends JpaRepository<DebugInfo, UUID> {
}