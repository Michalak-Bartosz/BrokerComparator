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
public class CPUMetric {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private BrokerType brokerType;
    @Column(precision = 5, scale = 2)
    private BigDecimal systemCpuUsagePercentage;
    @Column(precision = 5, scale = 2)
    private BigDecimal appCpuUsagePercentage;
}
