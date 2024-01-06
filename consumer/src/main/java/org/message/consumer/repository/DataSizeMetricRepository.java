package org.message.consumer.repository;

import org.message.consumer.entity.metric.DataSizeMetric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataSizeMetricRepository extends JpaRepository<DataSizeMetric, Long> {
}
