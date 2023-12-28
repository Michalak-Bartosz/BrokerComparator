package org.message.model.metric;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemoryMetric {
    private BigDecimal initialMemoryGB;
    private BigDecimal usedHeapMemoryGB;
    private BigDecimal maxHeapMemoryGB;
    private BigDecimal committedMemoryGB;
}
