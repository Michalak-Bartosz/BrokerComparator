package org.message.comparator.service.data;

import lombok.RequiredArgsConstructor;
import org.message.comparator.entity.data.DebugInfo;
import org.message.comparator.entity.data.TestReport;
import org.message.comparator.entity.data.User;
import org.message.comparator.entity.data.metric.*;
import org.message.comparator.exception.TestReportAlreadyExistException;
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
    private final ReportTimeMetricService reportTimeMetricService;

    public List<TestReport> getTestReport() {
        return testReportRepository.findAll();
    }

    public void createTestReport(UUID testUUID) {
        Optional<TestReport> testReportOptional = testReportRepository.findById(testUUID);

        if (testReportOptional.isPresent()) {
            throw new TestReportAlreadyExistException(testUUID);
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
        ReportTimeMetric reportTimeMetric = reportTimeMetricService.saveReportMemoryMetric(debugInfoList);
        Integer numberOfAttempts = debugInfoList.stream().map(DebugInfo::getNumberOfAttempt).max(Integer::compareTo).orElse(0);

        TestReport testReport = TestReport.builder()
                .testUUID(testUUID)
                .numberOfAttempts(numberOfAttempts)
                .userList(userList)
                .debugInfoList(debugInfoList)
                .producerReportCPUMetric(producerReportCPUMetric)
                .producerReportMemoryMetric(producerReportMemoryMetric)
                .consumerReportCPUMetric(consumerReportCPUMetric)
                .consumerReportMemoryMetric(consumerReportMemoryMetric)
                .reportTimeMetric(reportTimeMetric)
                .build();
        testReportRepository.save(testReport);
        testReportRepository.flush();
    }
}
