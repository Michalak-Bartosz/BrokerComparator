package org.message.consumer.util.metric;


import com.sun.management.OperatingSystemMXBean;
import lombok.experimental.UtilityClass;
import org.message.model.metric.CPUMetric;
import org.message.model.metric.MemoryMetric;
import org.message.model.util.BrokerType;

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

    public static MemoryMetric getMemoryMetrics(BrokerType brokerType) {
        return MemoryMetric.builder()
                .brokerType(brokerType)
                .initialMemoryGB(BigDecimal.valueOf(memoryMXBean.getHeapMemoryUsage().getInit()).divide(GB, 3, RoundingMode.UP))
                .usedHeapMemoryGB(BigDecimal.valueOf(memoryMXBean.getHeapMemoryUsage().getUsed()).divide(GB, 3, RoundingMode.UP))
                .maxHeapMemoryGB(BigDecimal.valueOf(memoryMXBean.getHeapMemoryUsage().getMax()).divide(GB, 3, RoundingMode.UP))
                .committedMemoryGB(BigDecimal.valueOf(memoryMXBean.getHeapMemoryUsage().getCommitted()).divide(GB, 3, RoundingMode.UP))
                .build();
    }

    public static CPUMetric getCpuMetrics(
            BigDecimal systemAverageCpu,
            BigDecimal appAverageCpu,
            BrokerType brokerType) {
        return CPUMetric.builder()
                .brokerType(brokerType)
                .systemCpuUsagePercentage(systemAverageCpu)
                .appCpuUsagePercentage(appAverageCpu)
                .build();
    }

    public static BigDecimal getAverageCpuPercentage(BigDecimal valBefore, BigDecimal valAfter) {
        return valBefore.add(valAfter).divide(BigDecimal.valueOf(2), 2, RoundingMode.UP);
    }

    public static BigDecimal getSystemCpuUsagePercentage() {
        return BigDecimal.valueOf(osBean.getCpuLoad()).multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.UP);
    }

    public static BigDecimal getAppCpuUsagePercentage() {
        return BigDecimal.valueOf(osBean.getProcessCpuLoad()).multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.UP);
    }
}
