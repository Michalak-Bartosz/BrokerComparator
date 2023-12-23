package org.message.comparator.entity.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.message.comparator.entity.data.metric.ReportCPUMetric;
import org.message.comparator.entity.data.metric.ReportMemoryMetric;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class TestReport {
    @Id
    private UUID testUUID;
    @OneToMany
    private List<User> userList;
    @OneToMany
    private List<DebugInfo> debugInfoList;
    @OneToOne
    private ReportCPUMetric producerReportCPUMetric;
    @OneToOne
    private ReportMemoryMetric producerReportMemoryMetric;
    @OneToOne
    private ReportCPUMetric consumerReportCPUMetric;
    @OneToOne
    private ReportMemoryMetric consumerReportMemoryMetric;
}
