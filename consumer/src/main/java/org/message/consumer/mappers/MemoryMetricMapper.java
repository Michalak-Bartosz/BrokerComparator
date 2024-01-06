package org.message.consumer.mappers;

import lombok.experimental.UtilityClass;
import org.message.consumer.entity.metric.MemoryMetric;

@UtilityClass
public class MemoryMetricMapper {

    public static MemoryMetric mapMemoryMetricsModelToEntity(org.message.model.metric.MemoryMetric memoryMetricModel) {
        return MemoryMetric.builder()
                .brokerType(memoryMetricModel.getBrokerType())
                .initialMemoryGB(memoryMetricModel.getInitialMemoryGB())
                .usedHeapMemoryGB(memoryMetricModel.getUsedHeapMemoryGB())
                .maxHeapMemoryGB(memoryMetricModel.getMaxHeapMemoryGB())
                .committedMemoryGB(memoryMetricModel.getCommittedMemoryGB())
                .build();
    }
}
