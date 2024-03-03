package org.message.consumer.service;

import lombok.RequiredArgsConstructor;
import org.message.consumer.entity.DebugInfo;
import org.message.consumer.entity.metric.CPUMetric;
import org.message.consumer.entity.metric.DataSizeMetric;
import org.message.consumer.entity.metric.MemoryMetric;
import org.message.consumer.repository.DebugInfoRepository;
import org.message.consumer.util.DurationUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DebugInfoService {

    private final DebugInfoRepository debugInfoRepository;
    private final MemoryMetricService memoryMetricService;
    private final CPUMetricService cpuMetricService;
    private final DataSizeMetricService dataSizeMetricService;

    @Transactional
    public synchronized void saveDebugInfoModel(org.message.model.DebugInfo debugInfoModel) {
        MemoryMetric producerMemoryMetric = memoryMetricService.saveMemoryMetricModel(debugInfoModel.getProducerMemoryMetrics());
        MemoryMetric consumerMemoryMetric = memoryMetricService.saveMemoryMetricModel(debugInfoModel.getConsumerMemoryMetrics());
        CPUMetric producerCpuMetric = cpuMetricService.saveCPUMetricModel(debugInfoModel.getProducerCPUMetrics());
        CPUMetric consumerCpuMetric = cpuMetricService.saveCPUMetricModel(debugInfoModel.getConsumerCPUMetrics());
        DataSizeMetric dataSizeMetric = dataSizeMetricService.saveDataSizeMetricModel(debugInfoModel.getDataSizeMetric());

        DebugInfo debugInfo = DebugInfo.builder()
                .uuid(debugInfoModel.getUuid())
                .testUUID(debugInfoModel.getTestUUID())
                .userUUID(debugInfoModel.getUserUUID())
                .isSync(debugInfoModel.getIsSync())
                .numberOfAttempt(debugInfoModel.getNumberOfAttempt())
                .delayBetweenAttemptsInMilliseconds(debugInfoModel.getDelayBetweenAttemptsInMilliseconds())
                .brokerType(debugInfoModel.getBrokerType())
                .testStatusPercentage(debugInfoModel.getTestStatusPercentage())
                .producedTimestamp(debugInfoModel.getProducedTimestamp())
                .consumedTimestamp(debugInfoModel.getConsumedTimestamp())
                .deltaTimestamp(debugInfoModel.getDeltaTimestamp())
                .formattedDeltaTimestamp(DurationUtil.humanReadableDuration(debugInfoModel.getDeltaTimestamp()))
                .countOfProducedMessages(debugInfoModel.getCountOfProducedMessages())
                .countOfConsumedMessages(debugInfoModel.getCountOfConsumedMessages())
                .dataSizeMetric(dataSizeMetric)
                .producerMemoryMetrics(producerMemoryMetric)
                .consumerMemoryMetrics(consumerMemoryMetric)
                .producerCPUMetrics(producerCpuMetric)
                .consumerCPUMetrics(consumerCpuMetric)
                .build();
        debugInfoRepository.save(debugInfo);
        debugInfoRepository.flush();
    }
}
