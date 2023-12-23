package org.message.comparator.repository.data;

import org.message.comparator.entity.data.metric.ReportCPUMetric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportCPUMetricRepository extends JpaRepository<ReportCPUMetric, Long> {
}
