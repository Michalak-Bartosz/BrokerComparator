package org.message.comparator.entity.data.metric;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.message.comparator.util.metric.ReportMemoMetricUtil;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class ReportMemoryMetric {
    @Id
    @GeneratedValue
    private Long id;
    private double initialMemoryGB;
    private double maxHeapMemoryGB;
    private double maxUsedHeapMemoryGB;
    private double minUsedHeapMemoryGB;
    private double averageUsedHeapMemoryGB;
    private double maxCommittedMemoryGB;
    private double minCommittedMemoryGB;
    private double averageCommittedMemoryGB;

    public ReportMemoryMetric(List<MemoryMetric> memoryMetricList) {
        this.initialMemoryGB = ReportMemoMetricUtil.getInitialMemoryGB(memoryMetricList);
        this.maxHeapMemoryGB = ReportMemoMetricUtil.getMaxHeapMemoryGB(memoryMetricList);
        this.maxUsedHeapMemoryGB = ReportMemoMetricUtil.getMaxUsedHeapMemoryGB(memoryMetricList);
        this.minUsedHeapMemoryGB = ReportMemoMetricUtil.getMinUsedHeapMemoryGB(memoryMetricList);
        this.averageUsedHeapMemoryGB = ReportMemoMetricUtil.getAverageUsedHeapMemoryGB(memoryMetricList);
        this.maxCommittedMemoryGB = ReportMemoMetricUtil.getMaxCommittedMemoryGB(memoryMetricList);
        this.minCommittedMemoryGB = ReportMemoMetricUtil.getMinCommittedMemoryGB(memoryMetricList);
        this.averageCommittedMemoryGB = ReportMemoMetricUtil.getAverageCommittedMemoryGB(memoryMetricList);
    }
}
