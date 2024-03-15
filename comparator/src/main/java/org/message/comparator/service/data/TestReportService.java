package org.message.comparator.service.data;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.message.comparator.entity.data.BrokerInfoData;
import org.message.comparator.entity.data.DebugInfo;
import org.message.comparator.entity.data.TestReport;
import org.message.comparator.entity.data.User;
import org.message.comparator.entity.data.metric.*;
import org.message.comparator.entity.data.util.BrokerType;
import org.message.comparator.exception.NoDataToGenerateReportException;
import org.message.comparator.exception.TestReportAlreadyExistException;
import org.message.comparator.repository.data.DebugInfoRepository;
import org.message.comparator.repository.data.TestReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
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

    private static final Integer NUMBER_OF_DATA_CHECKS = 3;

    private final DebugInfoRepository debugInfoRepository;

    public List<TestReport> getTestReports() {
        return testReportRepository.findAll();
    }

    @Transactional
    public void createTestReport(UUID testUUID, int totalRecordNumber) {
        log.info("📖⚙️ Start creating test report. Test UUID: {}", testUUID);
        Optional<TestReport> testReportOptional = testReportRepository.findById(testUUID);

        if (testReportOptional.isPresent()) {
            throw new TestReportAlreadyExistException(testUUID);
        }

        List<DebugInfo> debugInfoList;
        log.info("🔧 Try to find the data connected with test UUID: {}", testUUID);
        boolean dataStatus;
        int dataCheckCounter = 0;
        do {
            debugInfoList = debugInfoService.getAllDebugInfoByTestUUID(testUUID);
            dataStatus = checkTheData(totalRecordNumber, debugInfoList.size(), dataCheckCounter);
            dataCheckCounter++;
        } while (!dataStatus);
        log.info("🛠️ The data found for test UUID: {}! Start generating report...", testUUID);

        List<User> userList = userService.getAllUsersByTestUUID(testUUID);
        List<CPUMetric> producerCpuMetricList = debugInfoList.stream().map(DebugInfo::getProducerCPUMetrics).toList();
        List<MemoryMetric> producerMemoryMetricList = debugInfoList.stream().map(DebugInfo::getProducerMemoryMetrics).toList();
        List<CPUMetric> consumerCpuMetricList = debugInfoList.stream().map(DebugInfo::getConsumerCPUMetrics).toList();
        List<MemoryMetric> consumerMemoryMetricList = debugInfoList.stream().map(DebugInfo::getConsumerMemoryMetrics).toList();
        List<DataSizeMetric> dataSizeMetricList = debugInfoList.stream().map(DebugInfo::getDataSizeMetric).toList();

        List<BrokerType> brokerTypeList = debugInfoList.stream().map(DebugInfo::getBrokerType).distinct().toList();
        Integer numberOfAttempts = debugInfoList.stream().map(DebugInfo::getNumberOfAttempt).max(Comparator.naturalOrder()).orElse(0);
        Boolean isSync = debugInfoList.stream().map(DebugInfo::getIsSync).findFirst().orElse(true);
        Long delayBetweenAttemptsInMilliseconds = debugInfoList.stream().map(DebugInfo::getDelayBetweenAttemptsInMilliseconds).findFirst().orElse(0L);

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
                .isSync(isSync)
                .numberOfAttempts(numberOfAttempts)
                .delayBetweenAttemptsInMilliseconds(delayBetweenAttemptsInMilliseconds)
                .formattedDelayBetweenAttempts(humanReadableTime(delayBetweenAttemptsInMilliseconds))
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
        log.info("✅ Report generated for test UUID {}!", testUUID);
    }

    private static void waitForDataInDatabase() {
        log.info("🕒 Waiting for data in database...");
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private boolean checkTheData(int totalRecordNumber, int debugInfoListSize, int dataCheckCounter) {
        if (debugInfoListSize != totalRecordNumber) {
            if (dataCheckCounter < NUMBER_OF_DATA_CHECKS) {
                log.info("❌ No data to generate report!;\n -> Found {} debugInfo;\nShould be {} records\nNumber of left data checks: {}", debugInfoListSize, totalRecordNumber, NUMBER_OF_DATA_CHECKS - dataCheckCounter);
                waitForDataInDatabase(); // Wait 5s to save the data by consumer service after test.
                return false;
            }
            throw new NoDataToGenerateReportException();
        }
        return true;
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

    private static String humanReadableTime(Long timeInMilliseconds) {
        return DurationFormatUtils.formatDuration(timeInMilliseconds, "HH'h' mm'min' ss's' SSS'ms'", false);
    }
}
