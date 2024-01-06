package org.message.comparator.service.data;

import lombok.RequiredArgsConstructor;
import org.message.comparator.entity.data.metric.CPUMetric;
import org.message.comparator.entity.data.metric.ReportCPUMetric;
import org.message.comparator.entity.data.util.BrokerType;
import org.message.comparator.repository.data.ReportCPUMetricRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportCPUMetricService {

    private final ReportCPUMetricRepository reportCPUMetricRepository;

    public ReportCPUMetric saveReportCPUMetric(List<CPUMetric> cpuMetricList, BrokerType brokerType) {
        ReportCPUMetric reportCPUMetric = new ReportCPUMetric(cpuMetricList, brokerType);
        reportCPUMetricRepository.save(reportCPUMetric);
        reportCPUMetricRepository.flush();
        return reportCPUMetric;
    }
}
