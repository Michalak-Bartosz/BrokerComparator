package org.message.consumer.service;

import lombok.RequiredArgsConstructor;
import org.message.consumer.entity.DebugInfo;
import org.message.consumer.entity.metric.CPUMetric;
import org.message.consumer.entity.metric.MemoryMetric;
import org.message.consumer.repository.DebugInfoRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DebugInfoService {

    private final DebugInfoRepository debugInfoRepository;
    private final MemoryMetricService memoryMetricService;
    private final CPUMetricService cpuMetricService;

    public void saveDebugInfoModel(org.message.model.DebugInfo debugInfoModel) {
        MemoryMetric producerMemoryMetric = memoryMetricService.saveMemoryMetricModel(debugInfoModel.getProducerMemoryMetrics());
        MemoryMetric consumerMemoryMetric = memoryMetricService.saveMemoryMetricModel(debugInfoModel.getConsumerMemoryMetrics());
        CPUMetric producerCpuMetric = cpuMetricService.saveCPUMetricModel(debugInfoModel.getProducerCPUMetrics());
        CPUMetric consumerCpuMetric = cpuMetricService.saveCPUMetricModel(debugInfoModel.getConsumerCPUMetrics());

        DebugInfo debugInfo = DebugInfo.builder()
                .uuid(debugInfoModel.getUuid())
                .testUUID(debugInfoModel.getTestUUID())
                .numberOfAttempt(debugInfoModel.getNumberOfAttempt())
                .brokerType(debugInfoModel.getBrokerType())
                .testStatusPercentage(debugInfoModel.getTestStatusPercentage())
                .producedTimestamp(debugInfoModel.getProducedTimestamp())
                .consumedTimestamp(debugInfoModel.getConsumedTimestamp())
                .deltaTimestamp(debugInfoModel.getDeltaTimestamp())
                .countOfProducedMessages(debugInfoModel.getCountOfProducedMessages())
                .countOfConsumedMessages(debugInfoModel.getCountOfConsumedMessages())
                .producerMemoryMetrics(producerMemoryMetric)
                .consumerMemoryMetrics(consumerMemoryMetric)
                .producerCPUMetrics(producerCpuMetric)
                .consumerCPUMetrics(consumerCpuMetric)
                .build();
        debugInfoRepository.save(debugInfo);
        debugInfoRepository.flush();
    }
}
