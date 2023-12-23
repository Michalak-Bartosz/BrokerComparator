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
public class CPUMetric {
    @Id
    @GeneratedValue
    private Long id;
    private double systemCpuUsagePercentage;
    private double appCpuUsagePercentage;
}
