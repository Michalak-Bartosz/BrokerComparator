package org.message.comparator.entity.data.metric;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

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
    private Integer payloadSizeInBytes;
    private Integer producedDataSizeInBytes;
    private Integer consumedDataSizeInBytes;
}
