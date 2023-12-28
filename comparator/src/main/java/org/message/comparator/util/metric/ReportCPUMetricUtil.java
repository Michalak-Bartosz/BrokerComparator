package org.message.comparator.util.metric;

import lombok.experimental.UtilityClass;
import org.message.comparator.entity.data.metric.CPUMetric;
import org.message.comparator.exception.ReportMetricCalculateException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@UtilityClass
public class ReportCPUMetricUtil {

    public static BigDecimal getMaxSystemCpuUsagePercentage(List<CPUMetric> cpuMetricList) {
        return cpuMetricList.stream().map(CPUMetric::getSystemCpuUsagePercentage)
                .max(BigDecimal::compareTo)
                .orElseThrow(() -> new ReportMetricCalculateException(ReportCPUMetricUtil.class.getSimpleName(), "Get max system cpu usage percentage exception!"))
                .setScale(2, RoundingMode.UP);
    }

    public static BigDecimal getMaxAppCpuUsagePercentage(List<CPUMetric> cpuMetricList) {
        return cpuMetricList.stream().map(CPUMetric::getAppCpuUsagePercentage)
                .max(BigDecimal::compareTo)
                .orElseThrow(() -> new ReportMetricCalculateException(ReportCPUMetricUtil.class.getSimpleName(), "Get max app cpu usage percentage exception!"))
                .setScale(2, RoundingMode.UP);
    }

    public static BigDecimal getMinSystemCpuUsagePercentage(List<CPUMetric> cpuMetricList) {
        return cpuMetricList.stream().map(CPUMetric::getSystemCpuUsagePercentage)
                .min(BigDecimal::compareTo)
                .orElseThrow(() -> new ReportMetricCalculateException(ReportCPUMetricUtil.class.getSimpleName(), "Get min system cpu usage percentage exception!"))
                .setScale(2, RoundingMode.UP);
    }

    public static BigDecimal getMinAppCpuUsagePercentage(List<CPUMetric> cpuMetricList) {
        return cpuMetricList.stream().map(CPUMetric::getAppCpuUsagePercentage)
                .min(BigDecimal::compareTo)
                .orElseThrow(() -> new ReportMetricCalculateException(ReportCPUMetricUtil.class.getSimpleName(), "Get min app cpu usage percentage exception!"))
                .setScale(2, RoundingMode.UP);
    }

    public static BigDecimal getAverageSystemCpuUsagePercentage(List<CPUMetric> cpuMetricList) {
        BigDecimal sum = cpuMetricList.stream().map(CPUMetric::getSystemCpuUsagePercentage).reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum.divide(BigDecimal.valueOf(cpuMetricList.size()), 2, RoundingMode.UP);
    }

    public static BigDecimal getAverageAppCpuUsagePercentage(List<CPUMetric> cpuMetricList) {
        BigDecimal sum = cpuMetricList.stream().map(CPUMetric::getAppCpuUsagePercentage).reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum.divide(BigDecimal.valueOf(cpuMetricList.size()), 2, RoundingMode.UP);
    }
}
