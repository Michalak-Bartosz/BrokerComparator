package org.message.comparator.entity.data.metric;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.message.comparator.entity.data.DebugInfo;
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
    private Duration minDeltaTime;
    private Duration maxDeltaTime;
    private Duration averageDeltaTime;
    private Duration producedTime;
    private Duration consumedTime;

    public ReportTimeMetric(List<DebugInfo> debugInfoList) {
        this.minDeltaTime = ReportTimeMetricUtil.getMinDeltaTime(debugInfoList);
        this.maxDeltaTime = ReportTimeMetricUtil.getMaxDeltaTime(debugInfoList);
        this.averageDeltaTime = ReportTimeMetricUtil.getAverageDeltaTime(debugInfoList);
        this.producedTime = ReportTimeMetricUtil.producedTime(debugInfoList);
        this.consumedTime = ReportTimeMetricUtil.consumedTime(debugInfoList);
    }
}
