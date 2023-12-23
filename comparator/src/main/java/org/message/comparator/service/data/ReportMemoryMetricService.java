package org.message.comparator.service.data;

import lombok.RequiredArgsConstructor;
import org.message.comparator.entity.data.metric.MemoryMetric;
import org.message.comparator.entity.data.metric.ReportMemoryMetric;
import org.message.comparator.repository.data.ReportMemoryMetricRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportMemoryMetricService {

    private final ReportMemoryMetricRepository reportMemoryMetricRepository;

    public ReportMemoryMetric saveReportMemoryMetric(List<MemoryMetric> memoryMetricList) {
        ReportMemoryMetric reportMemoryMetric = new ReportMemoryMetric(memoryMetricList);
        reportMemoryMetricRepository.save(reportMemoryMetric);
        reportMemoryMetricRepository.flush();
        return reportMemoryMetric;
    }
}
