package org.message.comparator.util.metric;

import lombok.experimental.UtilityClass;
import org.message.comparator.entity.data.metric.MemoryMetric;
import org.message.comparator.exception.ReportMetricCalculateException;

import java.util.List;

@UtilityClass
public class ReportMemoMetricUtil {

    public static double getInitialMemoryGB(List<MemoryMetric> memoryMetricList) {
        return memoryMetricList.stream().map(MemoryMetric::getInitialMemoryGB)
                .findAny()
                .orElseThrow(() -> new ReportMetricCalculateException(ReportMemoMetricUtil.class.getSimpleName(), "Get initial memory [GB] exception!"));
    }

    public static double getMaxHeapMemoryGB(List<MemoryMetric> memoryMetricList) {
        return memoryMetricList.stream().map(MemoryMetric::getMaxHeapMemoryGB)
                .findAny()
                .orElseThrow(() -> new ReportMetricCalculateException(ReportMemoMetricUtil.class.getSimpleName(), "Get max heap memory [GB] exception!"));
    }

    public static double getMaxUsedHeapMemoryGB(List<MemoryMetric> memoryMetricList) {
        return memoryMetricList.stream().map(MemoryMetric::getUsedHeapMemoryGB)
                .max(Double::compareTo)
                .orElseThrow(() -> new ReportMetricCalculateException(ReportMemoMetricUtil.class.getSimpleName(), "Get max used heap memory [GB] exception!"));
    }

    public static double getMaxCommittedMemoryGB(List<MemoryMetric> memoryMetricList) {
        return memoryMetricList.stream().map(MemoryMetric::getCommittedMemoryGB)
                .max(Double::compareTo)
                .orElseThrow(() -> new ReportMetricCalculateException(ReportMemoMetricUtil.class.getSimpleName(), "Get max committed memory [GB] exception!"));
    }

    public static double getMinUsedHeapMemoryGB(List<MemoryMetric> memoryMetricList) {
        return memoryMetricList.stream().map(MemoryMetric::getUsedHeapMemoryGB)
                .min(Double::compareTo)
                .orElseThrow(() -> new ReportMetricCalculateException(ReportMemoMetricUtil.class.getSimpleName(), "Get min used heap memory [GB] exception!"));
    }

    public static double getMinCommittedMemoryGB(List<MemoryMetric> memoryMetricList) {
        return memoryMetricList.stream().map(MemoryMetric::getCommittedMemoryGB)
                .min(Double::compareTo)
                .orElseThrow(() -> new ReportMetricCalculateException(ReportMemoMetricUtil.class.getSimpleName(), "Get min committed memory [GB] exception!"));
    }

    public static double getAverageUsedHeapMemoryGB(List<MemoryMetric> memoryMetricList) {
        double sum = memoryMetricList.stream().map(MemoryMetric::getUsedHeapMemoryGB).mapToDouble(Double::doubleValue).sum();
        return sum / memoryMetricList.size();
    }

    public static double getAverageCommittedMemoryGB(List<MemoryMetric> memoryMetricList) {
        double sum = memoryMetricList.stream().map(MemoryMetric::getCommittedMemoryGB).mapToDouble(Double::doubleValue).sum();
        return sum / memoryMetricList.size();
    }
}
