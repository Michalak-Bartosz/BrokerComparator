package org.message.consumer.mappers;

import lombok.experimental.UtilityClass;
import org.message.consumer.entity.metric.CPUMetric;

@UtilityClass
public class CPUMetricMapper {

    public static CPUMetric mapCPUMetricModelToEntity(org.message.model.metric.CPUMetric cpuMetricModel) {
        return CPUMetric.builder()
                .systemCpuUsagePercentage(cpuMetricModel.getSystemCpuUsagePercentage())
                .appCpuUsagePercentage(cpuMetricModel.getAppCpuUsagePercentage())
                .build();
    }
}
