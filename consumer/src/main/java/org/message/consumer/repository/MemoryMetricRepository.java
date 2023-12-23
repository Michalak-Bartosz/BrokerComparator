package org.message.consumer.repository;

import org.message.consumer.entity.metric.MemoryMetric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemoryMetricRepository extends JpaRepository<MemoryMetric, Long> {
}
