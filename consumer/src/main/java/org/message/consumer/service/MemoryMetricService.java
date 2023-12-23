package org.message.consumer.service;

import lombok.RequiredArgsConstructor;
import org.message.consumer.entity.metric.MemoryMetric;
import org.message.consumer.mappers.MemoryMetricMapper;
import org.message.consumer.repository.MemoryMetricRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemoryMetricService {

    private final MemoryMetricRepository memoryMetricRepository;

    public MemoryMetric saveMemoryMetricModel(org.message.model.metric.MemoryMetric memoryMetricModel) {
        MemoryMetric memoryMetric = MemoryMetricMapper.mapMemoryMetricsModelToEntity(memoryMetricModel);
        memoryMetricRepository.save(memoryMetric);
        memoryMetricRepository.flush();
        return memoryMetric;
    }
}
