package org.message.consumer.entity.metric;

import jakarta.persistence.*;
import lombok.*;
import org.message.model.util.BrokerType;

import java.math.BigDecimal;

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
    @Enumerated(EnumType.STRING)
    private BrokerType brokerType;
    @Column(scale=3)
    private BigDecimal initialMemoryGB;
    @Column(scale=3)
    private BigDecimal usedHeapMemoryGB;
    @Column(scale=3)
    private BigDecimal maxHeapMemoryGB;
    @Column(scale=3)
    private BigDecimal committedMemoryGB;
}
