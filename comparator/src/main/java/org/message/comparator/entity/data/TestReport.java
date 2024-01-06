package org.message.comparator.entity.data;

import jakarta.persistence.*;
import lombok.*;
import org.message.comparator.entity.data.metric.ReportCPUMetric;
import org.message.comparator.entity.data.metric.ReportDataSizeMetric;
import org.message.comparator.entity.data.metric.ReportMemoryMetric;
import org.message.comparator.entity.data.metric.ReportTimeMetric;
import org.message.comparator.entity.data.util.BrokerType;

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
    private Integer numberOfAttempts;
    private List<BrokerType> brokerTypeList;

    @OneToMany(fetch = FetchType.EAGER)
    private List<User> userList;
    @OneToMany(fetch = FetchType.EAGER)
    private List<DebugInfo> debugInfoList;
    @OneToOne
    private ReportCPUMetric producerReportCPUMetric;
    @OneToOne
    private ReportMemoryMetric producerReportMemoryMetric;
    @OneToOne
    private ReportCPUMetric consumerReportCPUMetric;
    @OneToOne
    private ReportMemoryMetric consumerReportMemoryMetric;
    @OneToOne
    private ReportTimeMetric reportTimeMetric;
    @OneToOne
    private ReportDataSizeMetric reportDataSizeMetric;

    @OneToMany(fetch = FetchType.EAGER)
    private List<BrokerInfoData> brokerInfoDataList;
}
