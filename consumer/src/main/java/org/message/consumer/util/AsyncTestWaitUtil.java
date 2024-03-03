package org.message.consumer.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.message.consumer.dto.FinishAsyncTestDto;
import org.message.consumer.exception.WaitToFinishConsumeException;
import org.message.model.util.BrokerType;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

import static org.message.consumer.config.ApiConstants.PRODUCER_API_URL_ADDRESS;

@Slf4j
@UtilityClass
public class AsyncTestWaitUtil {

    private static final RestClient REST_CLIENT = RestClient.create();
    private static final long WAIT_EXECUTOR_TIMEOUT_MS = 5000;

    private static List<BrokerType> notifiedBrokers;

    public static synchronized void waitToFinishConsume(FinishAsyncTestDto finishAsyncTestDto) {
        notifiedBrokers = new ArrayList<>();
        log.info("🕒 Waiting to finish to consume in async test...");
        try {
            while (finishAsyncTestDto.getTotalNumberOfMessagesInTest() != TestProgressUtil.getTotalMessagesObtained()) {
                AsyncTestWaitUtil.class.wait(WAIT_EXECUTOR_TIMEOUT_MS);
                checkIfBrokerConsumedAllMessages(finishAsyncTestDto);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new WaitToFinishConsumeException(e);
        }
    }

    private static void checkIfBrokerConsumedAllMessages(FinishAsyncTestDto finishAsyncTestDto) {
        log.info("❓ Check if broker consumed all messages...");
        for (BrokerType brokerType : finishAsyncTestDto.getBrokerTotalNumberOfMessagesMap().keySet()) {
            int brokerTotalNumberOfMessages = finishAsyncTestDto.getBrokerTotalNumberOfMessagesMap().get(brokerType);
            int brokerMessagesObtained = TestProgressUtil.getBrokerTotalMessagesObtained(brokerType);
            if (TestProgressUtil.getBrokerTotalMessagesObtained(brokerType) == brokerTotalNumberOfMessages &&
                    !finishAsyncTestDto.getTotalNumberOfMessagesInTest().equals(finishAsyncTestDto.getBrokerTotalNumberOfMessagesMap().get(brokerType)) &&
                    !notifiedBrokers.contains(brokerType)) {
                notifyProducer(brokerType);
            }
            log.info("\t📨 Broker: {} obtained {} of {} messages", brokerType, brokerMessagesObtained, brokerTotalNumberOfMessages);
        }
    }

    private static synchronized void notifyProducer(BrokerType brokerType) {
        notifiedBrokers.add(brokerType);
        REST_CLIENT.post()
                .uri(PRODUCER_API_URL_ADDRESS + "/notify")
                .body(brokerType)
                .retrieve();
        log.info("🔔 Producer notified about consumed all data by broker: {}", brokerType);
    }
}
