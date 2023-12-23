package org.message.comparator.repository.data;

import org.message.comparator.entity.data.metric.ReportMemoryMetric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportMemoryMetricRepository extends JpaRepository<ReportMemoryMetric, Long> {
}
