package org.message.producer.util;


import com.sun.management.OperatingSystemMXBean;
import lombok.experimental.UtilityClass;
import org.message.model.metric.CPUMetric;
import org.message.model.metric.MemoryMetric;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.math.BigDecimal;
import java.math.RoundingMode;

@UtilityClass
public class MetricUtil {
    private final OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(
            OperatingSystemMXBean.class);
    private final MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

    public static final BigDecimal GB = BigDecimal.valueOf(1073741824.0);

    public static MemoryMetric getMemoryMetrics() {
        return MemoryMetric.builder()
                .initialMemoryGB(BigDecimal.valueOf(memoryMXBean.getHeapMemoryUsage().getInit()).divide(GB, 3, RoundingMode.UP))
                .usedHeapMemoryGB(BigDecimal.valueOf(memoryMXBean.getHeapMemoryUsage().getUsed()).divide(GB, 3, RoundingMode.UP))
                .maxHeapMemoryGB(BigDecimal.valueOf(memoryMXBean.getHeapMemoryUsage().getMax()).divide(GB, 3, RoundingMode.UP))
                .committedMemoryGB(BigDecimal.valueOf(memoryMXBean.getHeapMemoryUsage().getCommitted()).divide(GB, 3, RoundingMode.UP))
                .build();
    }

    public static CPUMetric getCpuMetrics(
            BigDecimal systemAverageCpu,
            BigDecimal appAverageCpu) {
        return CPUMetric.builder()
                .systemCpuUsagePercentage(systemAverageCpu)
                .appCpuUsagePercentage(appAverageCpu)
                .build();
    }

    public static BigDecimal getAverageCpuPercentage(BigDecimal valBefore, BigDecimal valAfter) {
        return valBefore.max(valAfter).subtract(valBefore.min(valAfter)).divide(BigDecimal.valueOf(2).multiply(BigDecimal.valueOf(100)), 2, RoundingMode.UP);
    }

    public static BigDecimal getSystemCpuUsagePercentage() {
        return BigDecimal.valueOf(osBean.getCpuLoad()).setScale(2, RoundingMode.UP);
    }

    public static BigDecimal getAppCpuUsagePercentage() {
        return BigDecimal.valueOf(osBean.getProcessCpuLoad()).setScale(2, RoundingMode.UP);
    }
}
