package org.message.comparator.entity.data.metric;

import jakarta.persistence.*;
import lombok.*;
import org.message.comparator.entity.data.util.BrokerType;

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
    @Enumerated(EnumType.STRING)
    private BrokerType brokerType;
    @Column(precision=5, scale=2)
    private BigDecimal systemCpuUsagePercentage;
    @Column(precision=5, scale=2)
    private BigDecimal appCpuUsagePercentage;
}
