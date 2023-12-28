package org.message.comparator.entity.data.metric;

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
public class CPUMetric {
    @Id
    @GeneratedValue
    private Long id;
    @Column(precision=5, scale=2)
    private BigDecimal systemCpuUsagePercentage;
    @Column(precision=5, scale=2)
    private BigDecimal appCpuUsagePercentage;
}
