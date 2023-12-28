package org.message.consumer.entity.metric;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

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
    @Column(scale=3)
    private BigDecimal initialMemoryGB;
    @Column(scale=3)
    private BigDecimal usedHeapMemoryGB;
    @Column(scale=3)
    private BigDecimal maxHeapMemoryGB;
    @Column(scale=3)
    private BigDecimal committedMemoryGB;
}
