package org.message.model.metric;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.message.model.util.BrokerType;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CPUMetric {
    private BrokerType brokerType;
    private BigDecimal systemCpuUsagePercentage;
    private BigDecimal appCpuUsagePercentage;
}
