package org.message.consumer.service;

import org.message.consumer.controller.HttpStreamController;
import org.message.consumer.dto.FinishTestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TestService {

    public static final AtomicInteger TOTAL_MESSAGES_OBTAINED = new AtomicInteger(0);

    @Transactional
    public boolean finishTest(FinishTestDto finishTestDto) {
        HttpStreamController.finishHttpStream();
        boolean isFinishSuccessful = finishTestDto.getNumberOfReceivedMessagesConsumer() == TOTAL_MESSAGES_OBTAINED.get();
        TOTAL_MESSAGES_OBTAINED.set(0);
        return isFinishSuccessful;
    }
}
