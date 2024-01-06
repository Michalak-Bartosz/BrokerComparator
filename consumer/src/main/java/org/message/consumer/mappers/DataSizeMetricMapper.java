package org.message.consumer.mappers;

import lombok.experimental.UtilityClass;
import org.message.consumer.entity.metric.DataSizeMetric;

@UtilityClass
public class DataSizeMetricMapper {

    public static DataSizeMetric mapDataSizeMetricModelToEntity(org.message.model.metric.DataSizeMetric dataSizeMetricModel) {
        return DataSizeMetric.builder()
                .brokerType(dataSizeMetricModel.getBrokerType())
                .payloadSizeInBytes(dataSizeMetricModel.getPayloadSizeInBytes())
                .producedDataSizeInBytes(dataSizeMetricModel.getProducedDataSizeInBytes())
                .consumedDataSizeInBytes(dataSizeMetricModel.getConsumedDataSizeInBytes())
                .build();
    }
}
