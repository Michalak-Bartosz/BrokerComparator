package org.message.model.metric;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CPUMetric {
    private double systemCpuUsagePercentage;
    private double appCpuUsagePercentage;
}
