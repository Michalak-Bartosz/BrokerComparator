package org.message.consumer.controller;

import lombok.RequiredArgsConstructor;
import org.message.consumer.dto.FinishTestDto;
import org.message.consumer.dto.RedirectFinishTestResponseDto;
import org.message.consumer.service.TestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private String getProducerApiResponse(boolean isProducerFinishTest, UUID testUUID) {
        return isProducerFinishTest ?
                String.format(CONSUMER_FINISH_TEST_RESPONSE, testUUID) :
                String.format(CONSUMER_EXCEPTION_FINISH_TEST_RESPONSE, testUUID, TestService.TOTAL_MESSAGES_OBTAINED);
    }
}
