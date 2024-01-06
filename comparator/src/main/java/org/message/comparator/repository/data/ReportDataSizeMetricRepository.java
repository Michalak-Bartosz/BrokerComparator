package org.message.comparator.repository.data;

import org.message.comparator.entity.data.metric.ReportDataSizeMetric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportDataSizeMetricRepository extends JpaRepository<ReportDataSizeMetric, Long> {
}
