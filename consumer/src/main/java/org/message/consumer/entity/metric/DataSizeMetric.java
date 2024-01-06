package org.message.consumer.entity.metric;

import jakarta.persistence.*;
import lombok.*;
import org.message.model.util.BrokerType;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class DataSizeMetric {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private BrokerType brokerType;
    private Integer payloadSizeInBytes;
    private Integer producedDataSizeInBytes;
    private Integer consumedDataSizeInBytes;
}
