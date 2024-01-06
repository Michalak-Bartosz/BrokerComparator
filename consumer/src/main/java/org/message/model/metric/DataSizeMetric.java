package org.message.model.metric;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.message.model.util.BrokerType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataSizeMetric {
    private BrokerType brokerType;
    private Integer payloadSizeInBytes;
    private Integer producedDataSizeInBytes;
    private Integer consumedDataSizeInBytes;
}
