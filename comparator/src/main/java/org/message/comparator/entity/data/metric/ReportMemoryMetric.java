package org.message.comparator.entity.data.metric;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.message.comparator.entity.data.util.BrokerType;
import org.message.comparator.util.metric.ReportMemoMetricUtil;

import java.math.BigDecimal;
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
    @Enumerated(EnumType.STRING)
    private BrokerType brokerType;
    @Column(scale = 3)
    private BigDecimal initialMemoryGB;
    @Column(scale = 3)
    private BigDecimal maxHeapMemoryGB;
    @Column(scale = 3)
    private BigDecimal maxUsedHeapMemoryGB;
    @Column(scale = 3)
    private BigDecimal minUsedHeapMemoryGB;
    @Column(scale = 3)
    private BigDecimal averageUsedHeapMemoryGB;
    @Column(scale = 3)
    private BigDecimal maxCommittedMemoryGB;
    @Column(scale = 3)
    private BigDecimal minCommittedMemoryGB;
    @Column(scale = 3)
    private BigDecimal averageCommittedMemoryGB;

    public ReportMemoryMetric(List<MemoryMetric> memoryMetricList, BrokerType brokerType) {
        this.brokerType = brokerType;
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
