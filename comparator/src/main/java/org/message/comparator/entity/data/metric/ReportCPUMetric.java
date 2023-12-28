package org.message.comparator.entity.data.metric;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.message.comparator.util.metric.ReportCPUMetricUtil;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class ReportCPUMetric {
    @Id
    @GeneratedValue
    private Long id;
    @Column(precision=5, scale=2)
    private BigDecimal maxSystemCpuUsagePercentage;
    @Column(precision=5, scale=2)
    private BigDecimal maxAppCpuUsagePercentage;
    @Column(precision=5, scale=2)
    private BigDecimal minSystemCpuUsagePercentage;
    @Column(precision=5, scale=2)
    private BigDecimal minAppCpuUsagePercentage;
    @Column(precision=5, scale=2)
    private BigDecimal averageSystemCpuUsagePercentage;
    @Column(precision=5, scale=2)
    private BigDecimal averageAppCpuUsagePercentage;

    public ReportCPUMetric(List<CPUMetric> cpuMetricList) {
        this.maxSystemCpuUsagePercentage = ReportCPUMetricUtil.getMaxSystemCpuUsagePercentage(cpuMetricList);
        this.maxAppCpuUsagePercentage = ReportCPUMetricUtil.getMaxAppCpuUsagePercentage(cpuMetricList);
        this.minSystemCpuUsagePercentage = ReportCPUMetricUtil.getMinSystemCpuUsagePercentage(cpuMetricList);
        this.minAppCpuUsagePercentage = ReportCPUMetricUtil.getMinAppCpuUsagePercentage(cpuMetricList);
        this.averageSystemCpuUsagePercentage = ReportCPUMetricUtil.getAverageSystemCpuUsagePercentage(cpuMetricList);
        this.averageAppCpuUsagePercentage = ReportCPUMetricUtil.getAverageAppCpuUsagePercentage(cpuMetricList);
    }
}
