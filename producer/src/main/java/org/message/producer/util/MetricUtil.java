package org.message.producer.util;


import com.sun.management.OperatingSystemMXBean;
import lombok.experimental.UtilityClass;
import org.message.model.metric.CPUMetric;
import org.message.model.metric.MemoryMetric;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

@UtilityClass
public class MetricUtil {
    private final OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(
            OperatingSystemMXBean.class);
    private final MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

    public static final double GB = 1073741824.0;

    public static MemoryMetric getMemoryMetrics() {
        return MemoryMetric.builder()
                .initialMemoryGB(memoryMXBean.getHeapMemoryUsage().getInit() / GB)
                .usedHeapMemoryGB(memoryMXBean.getHeapMemoryUsage().getUsed() / GB)
                .maxHeapMemoryGB(memoryMXBean.getHeapMemoryUsage().getMax() / GB)
                .committedMemoryGB(memoryMXBean.getHeapMemoryUsage().getCommitted() / GB)
                .build();
    }

    public static CPUMetric getCpuMetrics(
            double systemAverageCpu,
            double appAverageCpu) {
        return CPUMetric.builder()
                .systemCpuUsagePercentage(systemAverageCpu)
                .appCpuUsagePercentage(appAverageCpu)
                .build();
    }

    public static double getAverageCpuPercentage(double valBefore, double valAfter) {
        return (Math.max(valBefore, valAfter) - Math.min(valBefore, valAfter)) / 2.0 * 100.0;
    }

    public static double getSystemCpuUsagePercentage() {
        return osBean.getCpuLoad();
    }

    public static double getAppCpuUsagePercentage() {
        return osBean.getProcessCpuLoad();
    }
}
