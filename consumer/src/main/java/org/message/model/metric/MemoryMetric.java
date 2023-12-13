package org.message.model.metric;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemoryMetric {
    private double initialMemoryGB;
    private double usedHeapMemoryGB;
    private double maxHeapMemoryGB;
    private double committedMemoryGB;
}
