package org.message.consumer.service;

import lombok.RequiredArgsConstructor;
import org.message.consumer.entity.metric.CPUMetric;
import org.message.consumer.mappers.CPUMetricMapper;
import org.message.consumer.repository.CPUMetricRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CPUMetricService {

    private final CPUMetricRepository cpuMetricRepository;

    public CPUMetric saveCPUMetricModel(org.message.model.metric.CPUMetric cpuMetricModel) {
        CPUMetric cpuMetric = CPUMetricMapper.mapCPUMetricModelToEntity(cpuMetricModel);
        cpuMetricRepository.save(cpuMetric);
        cpuMetricRepository.flush();
        return cpuMetric;
    }
}
