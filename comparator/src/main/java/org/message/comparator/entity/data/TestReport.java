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
    private Boolean isSync;
    private Long delayBetweenAttemptsInMilliseconds;
    private String formattedDelayBetweenAttempts;
    private List<BrokerType> brokerTypeList;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<User> userList;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<DebugInfo> debugInfoList;
    @OneToOne(cascade = CascadeType.ALL)
    private ReportCPUMetric producerReportCPUMetric;
    @OneToOne(cascade = CascadeType.ALL)
    private ReportMemoryMetric producerReportMemoryMetric;
    @OneToOne(cascade = CascadeType.ALL)
    private ReportCPUMetric consumerReportCPUMetric;
    @OneToOne(cascade = CascadeType.ALL)
    private ReportMemoryMetric consumerReportMemoryMetric;
    @OneToOne(cascade = CascadeType.ALL)
    private ReportTimeMetric reportTimeMetric;
    @OneToOne(cascade = CascadeType.ALL)
    private ReportDataSizeMetric reportDataSizeMetric;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<BrokerInfoData> brokerInfoDataList;
}
