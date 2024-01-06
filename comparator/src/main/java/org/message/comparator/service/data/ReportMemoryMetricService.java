package org.message.comparator.service.data;

import lombok.RequiredArgsConstructor;
import org.message.comparator.entity.data.metric.MemoryMetric;
import org.message.comparator.entity.data.metric.ReportMemoryMetric;
import org.message.comparator.entity.data.util.BrokerType;
import org.message.comparator.repository.data.ReportMemoryMetricRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportMemoryMetricService {

    private final ReportMemoryMetricRepository reportMemoryMetricRepository;

    public ReportMemoryMetric saveReportMemoryMetric(List<MemoryMetric> memoryMetricList, BrokerType brokerType) {
        ReportMemoryMetric reportMemoryMetric = new ReportMemoryMetric(memoryMetricList, brokerType);
        reportMemoryMetricRepository.save(reportMemoryMetric);
        reportMemoryMetricRepository.flush();
        return reportMemoryMetric;
    }
}
