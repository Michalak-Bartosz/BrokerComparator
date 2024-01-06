package org.message.comparator.entity.data.metric;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.message.comparator.entity.data.DebugInfo;
import org.message.comparator.entity.data.util.BrokerType;
import org.message.comparator.util.metric.ReportTimeMetricUtil;

import java.time.Duration;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class ReportTimeMetric {
    @Id
    @GeneratedValue
    Long id;
    @Enumerated(EnumType.STRING)
    private BrokerType brokerType;
    private Duration minDeltaTime;
    private Duration maxDeltaTime;
    private Duration averageDeltaTime;
    private Duration producedTime;
    private Duration consumedTime;
    private String formattedMinDeltaTime;
    private String formattedMaxDeltaTime;
    private String formattedAverageDeltaTime;
    private String formattedProducedTime;
    private String formattedConsumedTime;

    public ReportTimeMetric(List<DebugInfo> debugInfoList, BrokerType brokerType) {
        this.brokerType = brokerType;
        this.minDeltaTime = ReportTimeMetricUtil.getMinDeltaTime(debugInfoList);
        this.maxDeltaTime = ReportTimeMetricUtil.getMaxDeltaTime(debugInfoList);
        this.averageDeltaTime = ReportTimeMetricUtil.getAverageDeltaTime(debugInfoList);
        this.producedTime = ReportTimeMetricUtil.getProducedTime(debugInfoList);
        this.consumedTime = ReportTimeMetricUtil.getConsumedTime(debugInfoList);
        this.formattedMinDeltaTime = ReportTimeMetricUtil.humanReadableDuration(minDeltaTime);
        this.formattedMaxDeltaTime = ReportTimeMetricUtil.humanReadableDuration(maxDeltaTime);
        this.formattedAverageDeltaTime = ReportTimeMetricUtil.humanReadableDuration(averageDeltaTime);
        this.formattedProducedTime = ReportTimeMetricUtil.humanReadableDuration(producedTime);
        this.formattedConsumedTime = ReportTimeMetricUtil.humanReadableDuration(consumedTime);
    }
}
