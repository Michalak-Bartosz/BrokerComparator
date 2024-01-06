package org.message.comparator.service.data;

import lombok.RequiredArgsConstructor;
import org.message.comparator.entity.data.metric.DataSizeMetric;
import org.message.comparator.entity.data.metric.ReportDataSizeMetric;
import org.message.comparator.entity.data.util.BrokerType;
import org.message.comparator.repository.data.ReportDataSizeMetricRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportDataSizeMetricService {

    private final ReportDataSizeMetricRepository reportDataSizeMetricRepository;

    public ReportDataSizeMetric saveReportDataSizeMetric(List<DataSizeMetric> dataSizeMetricList, Duration producedTime, Duration consumedTime, BrokerType brokerType) {
        ReportDataSizeMetric reportDataSizeMetric = new ReportDataSizeMetric(dataSizeMetricList, producedTime, consumedTime, brokerType);
        reportDataSizeMetricRepository.save(reportDataSizeMetric);
        reportDataSizeMetricRepository.flush();
        return reportDataSizeMetric;
    }
}
