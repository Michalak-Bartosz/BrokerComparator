package org.message.consumer.controller;

import lombok.RequiredArgsConstructor;
import org.message.consumer.dto.FinishAsyncTestDto;
import org.message.consumer.dto.FinishTestDto;
import org.message.consumer.dto.RedirectFinishAsyncTestResponseDto;
import org.message.consumer.dto.RedirectFinishTestResponseDto;
import org.message.consumer.service.TestService;
import org.message.consumer.util.TestProgressUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.message.consumer.config.ApiConstants.REQUEST_MAPPING_NAME;

@RestController
@RequestMapping(REQUEST_MAPPING_NAME + "/test")
@RequiredArgsConstructor
public class TestDataController {
    private static final String CONSUMER_FINISH_TEST_RESPONSE = "Request received. Test has been finished. Test UUID: %s";
    private static final String CONSUMER_EXCEPTION_FINISH_TEST_RESPONSE = "Request received. Test has been finished with failure. Test UUID: %s. Number of obtained messages: %s";
    private final TestService testService;

    @PostMapping("/finish")
    public ResponseEntity<RedirectFinishTestResponseDto> finishTest(@RequestBody FinishTestDto finishTestDto) {
        boolean isConsumerFinishTest = testService.finishTest(finishTestDto);
        return ResponseEntity.ok(RedirectFinishTestResponseDto.builder()
                .isConsumerFinishTest(isConsumerFinishTest)
                .consumerApiResponse(getProducerApiResponse(isConsumerFinishTest, finishTestDto.getTestUUID()))
                .build());
    }

    @PostMapping("/finish/async")
    public ResponseEntity<RedirectFinishAsyncTestResponseDto> finishAsyncTest(@RequestBody FinishAsyncTestDto finishAsyncTestDto) {
        boolean isConsumerFinishTest = testService.finishAsyncTest(finishAsyncTestDto);
        return ResponseEntity.ok(RedirectFinishAsyncTestResponseDto.builder()
                .isConsumerFinishTest(isConsumerFinishTest)
                .consumerApiResponse(getProducerApiResponse(isConsumerFinishTest, finishAsyncTestDto.getTestUUID()))
                .build());
    }

    private String getProducerApiResponse(boolean isProducerFinishTest, UUID testUUID) {
        return isProducerFinishTest ?
                String.format(CONSUMER_FINISH_TEST_RESPONSE, testUUID) :
                String.format(CONSUMER_EXCEPTION_FINISH_TEST_RESPONSE, testUUID, TestProgressUtil.getTotalMessagesObtained());
    }
}
