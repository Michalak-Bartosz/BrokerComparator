package org.message.consumer.entity.metric;

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
public class MemoryMetric {
    @Id
    @GeneratedValue
    private Long id;
    private double initialMemoryGB;
    private double usedHeapMemoryGB;
    private double maxHeapMemoryGB;
    private double committedMemoryGB;
}
