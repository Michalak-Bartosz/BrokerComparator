package org.message.consumer.service;

import lombok.RequiredArgsConstructor;
import org.message.consumer.entity.metric.DataSizeMetric;
import org.message.consumer.mappers.DataSizeMetricMapper;
import org.message.consumer.repository.DataSizeMetricRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataSizeMetricService {

    private final DataSizeMetricRepository dataSizeMetricRepository;

    public DataSizeMetric saveDataSizeMetricModel(org.message.model.metric.DataSizeMetric dataSizeMetricModel) {
        DataSizeMetric dataSizeMetric = DataSizeMetricMapper.mapDataSizeMetricModelToEntity(dataSizeMetricModel);
        dataSizeMetricRepository.save(dataSizeMetric);
        dataSizeMetricRepository.flush();
        return dataSizeMetric;
    }
}
