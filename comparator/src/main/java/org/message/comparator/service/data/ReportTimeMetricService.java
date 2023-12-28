package org.message.comparator.service.data;

import lombok.RequiredArgsConstructor;
import org.message.comparator.entity.data.DebugInfo;
import org.message.comparator.entity.data.metric.ReportTimeMetric;
import org.message.comparator.repository.data.ReportTimeMetricRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportTimeMetricService {

    private final ReportTimeMetricRepository reportTimeMetricRepository;

    public ReportTimeMetric saveReportMemoryMetric(List<DebugInfo> debugInfoList) {
        ReportTimeMetric reportTimeMetric = new ReportTimeMetric(debugInfoList);
        reportTimeMetricRepository.save(reportTimeMetric);
        reportTimeMetricRepository.flush();
        return reportTimeMetric;
    }
}
