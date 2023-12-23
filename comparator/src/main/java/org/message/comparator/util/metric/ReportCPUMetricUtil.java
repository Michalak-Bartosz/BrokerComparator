package org.message.comparator.util.metric;

import lombok.experimental.UtilityClass;
import org.message.comparator.entity.data.metric.CPUMetric;
import org.message.comparator.exception.ReportMetricCalculateException;

import java.util.List;

@UtilityClass
public class ReportCPUMetricUtil {

    public static double getMaxSystemCpuUsagePercentage(List<CPUMetric> cpuMetricList) {
        return cpuMetricList.stream().map(CPUMetric::getSystemCpuUsagePercentage)
                .max(Double::compareTo)
                .orElseThrow(() -> new ReportMetricCalculateException(ReportCPUMetricUtil.class.getSimpleName(), "Get max system cpu usage percentage exception!"));
    }

    public static double getMaxAppCpuUsagePercentage(List<CPUMetric> cpuMetricList) {
        return cpuMetricList.stream().map(CPUMetric::getAppCpuUsagePercentage)
                .max(Double::compareTo)
                .orElseThrow(() -> new ReportMetricCalculateException(ReportCPUMetricUtil.class.getSimpleName(), "Get max app cpu usage percentage exception!"));
    }

    public static double getMinSystemCpuUsagePercentage(List<CPUMetric> cpuMetricList) {
        return cpuMetricList.stream().map(CPUMetric::getSystemCpuUsagePercentage)
                .min(Double::compareTo)
                .orElseThrow(() -> new ReportMetricCalculateException(ReportCPUMetricUtil.class.getSimpleName(), "Get min system cpu usage percentage exception!"));
    }

    public static double getMinAppCpuUsagePercentage(List<CPUMetric> cpuMetricList) {
        return cpuMetricList.stream().map(CPUMetric::getAppCpuUsagePercentage)
                .min(Double::compareTo)
                .orElseThrow(() -> new ReportMetricCalculateException(ReportCPUMetricUtil.class.getSimpleName(), "Get min app cpu usage percentage exception!"));
    }

    public static double getAverageSystemCpuUsagePercentage(List<CPUMetric> cpuMetricList) {
        double sum = cpuMetricList.stream().map(CPUMetric::getSystemCpuUsagePercentage).mapToDouble(Double::doubleValue).sum();
        return sum / cpuMetricList.size();
    }

    public static double getAverageAppCpuUsagePercentage(List<CPUMetric> cpuMetricList) {
        double sum = cpuMetricList.stream().map(CPUMetric::getAppCpuUsagePercentage).mapToDouble(Double::doubleValue).sum();
        return sum / cpuMetricList.size();
    }
}
