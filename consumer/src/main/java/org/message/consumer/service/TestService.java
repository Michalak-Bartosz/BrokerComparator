package org.message.consumer.service;

import lombok.extern.slf4j.Slf4j;
import org.message.consumer.controller.HttpStreamController;
import org.message.consumer.dto.FinishAsyncTestDto;
import org.message.consumer.dto.FinishTestDto;
import org.message.consumer.util.TestProgressUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.message.consumer.util.AsyncTestWaitUtil.waitToFinishConsume;

@Slf4j
@Service
public class TestService {

    @Transactional
    public boolean finishTest(FinishTestDto finishTestDto) {
        HttpStreamController.finishHttpStream();
        boolean isFinishSuccessful = finishTestDto.getNumberOfReceivedMessagesConsumer() == TestProgressUtil.getTotalMessagesObtained();
        TestProgressUtil.setTotalMessagesObtained(0);
        TestProgressUtil.setBrokerTotalMessagesObtainedAllBrokers(0);
        return isFinishSuccessful;
    }

    @Transactional
    public boolean finishAsyncTest(FinishAsyncTestDto finishAsyncTestDto) {
        log.info("🔀🚩 Handle finish async test request");
        waitToFinishConsume(finishAsyncTestDto);
        TestProgressUtil.setTotalMessagesObtained(0);
        TestProgressUtil.setBrokerTotalMessagesObtainedAllBrokers(0);
        log.info("🔀🏁 Finish async test");
        return true;
    }


}
