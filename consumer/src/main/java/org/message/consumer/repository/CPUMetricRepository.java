package org.message.consumer.repository;

import org.message.consumer.entity.metric.CPUMetric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CPUMetricRepository extends JpaRepository<CPUMetric, Long> {
}
