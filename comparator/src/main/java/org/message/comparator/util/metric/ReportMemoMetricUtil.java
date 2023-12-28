package org.message.comparator.util.metric;

import lombok.experimental.UtilityClass;
import org.message.comparator.entity.data.metric.MemoryMetric;
import org.message.comparator.exception.ReportMetricCalculateException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@UtilityClass
public class ReportMemoMetricUtil {

    public static BigDecimal getInitialMemoryGB(List<MemoryMetric> memoryMetricList) {
        return memoryMetricList.stream().map(MemoryMetric::getInitialMemoryGB)
                .findAny()
                .orElseThrow(() -> new ReportMetricCalculateException(ReportMemoMetricUtil.class.getSimpleName(), "Get initial memory [GB] exception!"))
                .setScale(3, RoundingMode.UP);
    }

    public static BigDecimal getMaxHeapMemoryGB(List<MemoryMetric> memoryMetricList) {
        return memoryMetricList.stream().map(MemoryMetric::getMaxHeapMemoryGB)
                .findAny()
                .orElseThrow(() -> new ReportMetricCalculateException(ReportMemoMetricUtil.class.getSimpleName(), "Get max heap memory [GB] exception!"))
                .setScale(3, RoundingMode.UP);
    }

    public static BigDecimal getMaxUsedHeapMemoryGB(List<MemoryMetric> memoryMetricList) {
        return memoryMetricList.stream().map(MemoryMetric::getUsedHeapMemoryGB)
                .max(BigDecimal::compareTo)
                .orElseThrow(() -> new ReportMetricCalculateException(ReportMemoMetricUtil.class.getSimpleName(), "Get max used heap memory [GB] exception!"))
                .setScale(3, RoundingMode.UP);
    }

    public static BigDecimal getMaxCommittedMemoryGB(List<MemoryMetric> memoryMetricList) {
        return memoryMetricList.stream().map(MemoryMetric::getCommittedMemoryGB)
                .max(BigDecimal::compareTo)
                .orElseThrow(() -> new ReportMetricCalculateException(ReportMemoMetricUtil.class.getSimpleName(), "Get max committed memory [GB] exception!"))
                .setScale(3, RoundingMode.UP);
    }

    public static BigDecimal getMinUsedHeapMemoryGB(List<MemoryMetric> memoryMetricList) {
        return memoryMetricList.stream().map(MemoryMetric::getUsedHeapMemoryGB)
                .min(BigDecimal::compareTo)
                .orElseThrow(() -> new ReportMetricCalculateException(ReportMemoMetricUtil.class.getSimpleName(), "Get min used heap memory [GB] exception!"))
                .setScale(3, RoundingMode.UP);
    }

    public static BigDecimal getMinCommittedMemoryGB(List<MemoryMetric> memoryMetricList) {
        return memoryMetricList.stream().map(MemoryMetric::getCommittedMemoryGB)
                .min(BigDecimal::compareTo)
                .orElseThrow(() -> new ReportMetricCalculateException(ReportMemoMetricUtil.class.getSimpleName(), "Get min committed memory [GB] exception!"))
                .setScale(3, RoundingMode.UP);
    }

    public static BigDecimal getAverageUsedHeapMemoryGB(List<MemoryMetric> memoryMetricList) {
        BigDecimal sum = memoryMetricList.stream().map(MemoryMetric::getUsedHeapMemoryGB).reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum.divide(BigDecimal.valueOf(memoryMetricList.size()), 3, RoundingMode.UP);
    }

    public static BigDecimal getAverageCommittedMemoryGB(List<MemoryMetric> memoryMetricList) {
        BigDecimal sum = memoryMetricList.stream().map(MemoryMetric::getCommittedMemoryGB).reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum.divide(BigDecimal.valueOf(memoryMetricList.size()), 2, RoundingMode.UP);
    }
}
