package org.message.comparator.service.data;

import lombok.RequiredArgsConstructor;
import org.message.comparator.entity.data.DebugInfo;
import org.message.comparator.entity.data.TestReport;
import org.message.comparator.entity.data.User;
import org.message.comparator.entity.data.metric.CPUMetric;
import org.message.comparator.entity.data.metric.MemoryMetric;
import org.message.comparator.entity.data.metric.ReportCPUMetric;
import org.message.comparator.entity.data.metric.ReportMemoryMetric;
import org.message.comparator.repository.data.TestReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TestReportService {

    private final TestReportRepository testReportRepository;
    private final UserService userService;
    private final DebugInfoService debugInfoService;
    private final ReportCPUMetricService reportCPUMetricService;
    private final ReportMemoryMetricService reportMemoryMetricService;

    public TestReport getOrCreateTestReport(UUID testUUID) {
        Optional<TestReport> testReportOptional = testReportRepository.findById(testUUID);

        if (testReportOptional.isPresent()) {
            return testReportOptional.get();
        }

        List<User> userList = userService.getAllUsersByTestUUID(testUUID);
        List<DebugInfo> debugInfoList = debugInfoService.getAllDebugInfoByTestUUID(testUUID);
        List<CPUMetric> producerCpuMetricList = debugInfoList.stream().map(DebugInfo::getProducerCPUMetrics).toList();
        List<MemoryMetric> producerMemoryMetric = debugInfoList.stream().map(DebugInfo::getProducerMemoryMetrics).toList();
        List<CPUMetric> consumerCpuMetricList = debugInfoList.stream().map(DebugInfo::getConsumerCPUMetrics).toList();
        List<MemoryMetric> consumerMemoryMetric = debugInfoList.stream().map(DebugInfo::getConsumerMemoryMetrics).toList();

        ReportCPUMetric producerReportCPUMetric = reportCPUMetricService.saveReportCPUMetric(producerCpuMetricList);
        ReportMemoryMetric producerReportMemoryMetric = reportMemoryMetricService.saveReportMemoryMetric(producerMemoryMetric);
        ReportCPUMetric consumerReportCPUMetric = reportCPUMetricService.saveReportCPUMetric(consumerCpuMetricList);
        ReportMemoryMetric consumerReportMemoryMetric = reportMemoryMetricService.saveReportMemoryMetric(consumerMemoryMetric);

        TestReport testReport = TestReport.builder()
                .testUUID(testUUID)
                .userList(userList)
                .debugInfoList(debugInfoList)
                .producerReportCPUMetric(producerReportCPUMetric)
                .producerReportMemoryMetric(producerReportMemoryMetric)
                .consumerReportCPUMetric(consumerReportCPUMetric)
                .consumerReportMemoryMetric(consumerReportMemoryMetric)
                .build();
        testReportRepository.save(testReport);
        testReportRepository.flush();
        return testReport;
    }
}
