package org.message.comparator.service.data;

import lombok.RequiredArgsConstructor;
import org.message.comparator.entity.data.BrokerInfoData;
import org.message.comparator.entity.data.DebugInfo;
import org.message.comparator.entity.data.TestReport;
import org.message.comparator.entity.data.User;
import org.message.comparator.entity.data.metric.*;
import org.message.comparator.entity.data.util.BrokerType;
import org.message.comparator.exception.TestReportAlreadyExistException;
import org.message.comparator.repository.data.TestReportRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private final ReportDataSizeMetricService reportDataSizeMetricService;
    private final BrokerInfoDataService brokerInfoDataService;

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
        List<MemoryMetric> producerMemoryMetricList = debugInfoList.stream().map(DebugInfo::getProducerMemoryMetrics).toList();
        List<CPUMetric> consumerCpuMetricList = debugInfoList.stream().map(DebugInfo::getConsumerCPUMetrics).toList();
        List<MemoryMetric> consumerMemoryMetricList = debugInfoList.stream().map(DebugInfo::getConsumerMemoryMetrics).toList();
        List<DataSizeMetric> dataSizeMetricList = debugInfoList.stream().map(DebugInfo::getDataSizeMetric).toList();

        List<BrokerType> brokerTypeList = debugInfoList.stream().map(DebugInfo::getBrokerType).distinct().toList();
        Integer numberOfAttempts = debugInfoList.stream().map(DebugInfo::getNumberOfAttempt).max(Integer::compareTo).orElse(0);

        ReportCPUMetric producerReportCPUMetric = reportCPUMetricService.saveReportCPUMetric(producerCpuMetricList, BrokerType.ALL);
        ReportMemoryMetric producerReportMemoryMetric = reportMemoryMetricService.saveReportMemoryMetric(producerMemoryMetricList, BrokerType.ALL);
        ReportCPUMetric consumerReportCPUMetric = reportCPUMetricService.saveReportCPUMetric(consumerCpuMetricList, BrokerType.ALL);
        ReportMemoryMetric consumerReportMemoryMetric = reportMemoryMetricService.saveReportMemoryMetric(consumerMemoryMetricList, BrokerType.ALL);
        ReportTimeMetric reportTimeMetric = reportTimeMetricService.saveReportMemoryMetric(debugInfoList, BrokerType.ALL);
        ReportDataSizeMetric reportDataSizeMetric = reportDataSizeMetricService.saveReportDataSizeMetric(dataSizeMetricList, reportTimeMetric.getProducedTime(), reportTimeMetric.getConsumedTime(), BrokerType.ALL);
        List<BrokerInfoData> brokerInfoDataList = brokerInfoDataService.saveBrokerInfoDataList(getBrokerInfoDataList(testUUID, brokerTypeList));

        TestReport testReport = TestReport.builder()
                .testUUID(testUUID)
                .brokerTypeList(brokerTypeList)
                .numberOfAttempts(numberOfAttempts)
                .userList(userList)
                .debugInfoList(debugInfoList)
                .producerReportCPUMetric(producerReportCPUMetric)
                .producerReportMemoryMetric(producerReportMemoryMetric)
                .consumerReportCPUMetric(consumerReportCPUMetric)
                .consumerReportMemoryMetric(consumerReportMemoryMetric)
                .reportTimeMetric(reportTimeMetric)
                .reportDataSizeMetric(reportDataSizeMetric)
                .brokerInfoDataList(brokerInfoDataList)
                .build();
        testReportRepository.save(testReport);
        testReportRepository.flush();
    }

    private List<BrokerInfoData> getBrokerInfoDataList(UUID testUUID, List<BrokerType> brokerTypeList) {
        List<BrokerInfoData> brokerInfoDataList = new ArrayList<>();
        if (brokerTypeList.size() == 1) {
            return brokerInfoDataList;
        }
        brokerTypeList.forEach(brokerType -> brokerInfoDataList.add(getBrokerInfoData(testUUID, brokerType)));
        return brokerInfoDataList;
    }

    private BrokerInfoData getBrokerInfoData(UUID testUUID, BrokerType brokerType) {
        List<DebugInfo> debugInfoList = debugInfoService.getAllDebugInfoByTestUUIDAndBrokerType(testUUID, brokerType);
        List<User> userList = userService.getAllUsersByUUIDAndBrokerType(testUUID, brokerType);

        List<CPUMetric> producerCpuMetricList = debugInfoList.stream().map(DebugInfo::getProducerCPUMetrics).toList();
        List<MemoryMetric> producerMemoryMetricList = debugInfoList.stream().map(DebugInfo::getProducerMemoryMetrics).toList();
        List<CPUMetric> consumerCpuMetricList = debugInfoList.stream().map(DebugInfo::getConsumerCPUMetrics).toList();
        List<MemoryMetric> consumerMemoryMetricList = debugInfoList.stream().map(DebugInfo::getConsumerMemoryMetrics).toList();
        List<DataSizeMetric> dataSizeMetricList = debugInfoList.stream().map(DebugInfo::getDataSizeMetric).toList();

        ReportCPUMetric producerReportCPUMetric = reportCPUMetricService.saveReportCPUMetric(producerCpuMetricList, brokerType);
        ReportMemoryMetric producerReportMemoryMetric = reportMemoryMetricService.saveReportMemoryMetric(producerMemoryMetricList, brokerType);
        ReportCPUMetric consumerReportCPUMetric = reportCPUMetricService.saveReportCPUMetric(consumerCpuMetricList, brokerType);
        ReportMemoryMetric consumerReportMemoryMetric = reportMemoryMetricService.saveReportMemoryMetric(consumerMemoryMetricList, brokerType);
        ReportTimeMetric reportTimeMetric = reportTimeMetricService.saveReportMemoryMetric(debugInfoList, brokerType);
        ReportDataSizeMetric reportDataSizeMetric = reportDataSizeMetricService.saveReportDataSizeMetric(dataSizeMetricList, reportTimeMetric.getProducedTime(), reportTimeMetric.getConsumedTime(), brokerType);

        return BrokerInfoData.builder()
                .brokerType(brokerType)
                .userList(userList)
                .debugInfoList(debugInfoList)
                .producerReportCPUMetric(producerReportCPUMetric)
                .producerReportMemoryMetric(producerReportMemoryMetric)
                .consumerReportCPUMetric(consumerReportCPUMetric)
                .consumerReportMemoryMetric(consumerReportMemoryMetric)
                .reportTimeMetric(reportTimeMetric)
                .reportDataSizeMetric(reportDataSizeMetric)
                .build();
    }
}
