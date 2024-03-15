package org.message.comparator.entity.data.metric;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.message.comparator.entity.data.util.BrokerType;
import org.message.comparator.util.metric.ReportDataSizeMetricUtil;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class ReportDataSizeMetric {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private BrokerType brokerType;
    private Integer maxPayloadSizeInBytes;
    private Integer minPayloadSizeInBytes;
    private BigDecimal averagePayloadSizeInBytes;
    private String formattedMaxPayloadSize;
    private String formattedMinPayloadSize;
    private String formattedAveragePayloadSize;
    private Integer totalProducedDataSizeInBytes;
    private Integer totalConsumedDataSizeInBytes;
    private String formattedTotalProducedDataSize;
    private String formattedTotalConsumedDataSize;
    private BigDecimal producedDataSizeInBytesPerSecond;
    private BigDecimal consumedDataSizeInBytesPerSecond;
    private String formattedProducedDataSizePerSecond;
    private String formattedConsumedDataSizePerSecond;

    public ReportDataSizeMetric(List<DataSizeMetric> dataSizeMetrics, Duration producedTime, Duration consumedTime, BrokerType brokerType) {
        this.brokerType = brokerType;
        this.maxPayloadSizeInBytes = ReportDataSizeMetricUtil.getMaxPayloadSizeInBytes(dataSizeMetrics);
        this.minPayloadSizeInBytes = ReportDataSizeMetricUtil.getMinPayloadSizeInBytes(dataSizeMetrics);
        this.averagePayloadSizeInBytes = ReportDataSizeMetricUtil.getAveragePayloadSizeInBytes(dataSizeMetrics);
        this.formattedMaxPayloadSize = ReportDataSizeMetricUtil.getFormattedMaxPayloadSize(maxPayloadSizeInBytes);
        this.formattedMinPayloadSize = ReportDataSizeMetricUtil.getFormattedMinPayloadSize(minPayloadSizeInBytes);
        this.formattedAveragePayloadSize = ReportDataSizeMetricUtil.getFormattedAveragePayloadSize(averagePayloadSizeInBytes);
        this.totalProducedDataSizeInBytes = ReportDataSizeMetricUtil.getTotalProducedDataSizeInBytes(dataSizeMetrics);
        this.totalConsumedDataSizeInBytes = ReportDataSizeMetricUtil.getTotalConsumedDataSizeInBytes(dataSizeMetrics);
        this.formattedTotalProducedDataSize = ReportDataSizeMetricUtil.getFormattedTotalProducedDataSize(totalProducedDataSizeInBytes);
        this.formattedTotalConsumedDataSize = ReportDataSizeMetricUtil.getFormattedTotalConsumedDataSize(totalConsumedDataSizeInBytes);
        this.producedDataSizeInBytesPerSecond = ReportDataSizeMetricUtil.getProducedDataSizeInBytesPerSecond(totalProducedDataSizeInBytes, producedTime);
        this.consumedDataSizeInBytesPerSecond = ReportDataSizeMetricUtil.getConsumedDataSizeInBytesPerSecond(totalConsumedDataSizeInBytes, consumedTime);
        this.formattedProducedDataSizePerSecond = ReportDataSizeMetricUtil.getFormattedProducedDataSizePerSecond(producedDataSizeInBytesPerSecond);
        this.formattedConsumedDataSizePerSecond = ReportDataSizeMetricUtil.getFormattedConsumedDataSizePerSecond(consumedDataSizeInBytesPerSecond);
    }
}