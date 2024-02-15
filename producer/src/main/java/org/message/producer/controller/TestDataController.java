package org.message.producer.controller;

import lombok.RequiredArgsConstructor;
import org.message.producer.dto.FinishTestDto;
import org.message.producer.dto.RedirectFinishTestResponseDto;
import org.message.producer.dto.RedirectStartTestResponseDto;
import org.message.producer.dto.TestSettingsDto;
import org.message.producer.exception.HttpStreamRedirectException;
import org.message.producer.service.TestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.message.producer.config.ApiConstants.CONSUMER_API_URL_ADDRESS;
import static org.message.producer.config.ApiConstants.REQUEST_MAPPING_NAME;

@RestController
@RequestMapping(REQUEST_MAPPING_NAME + "/test")
@RequiredArgsConstructor
public class TestDataController {

    private static final String PRODUCER_START_TEST_RESPONSE = "Request received. Test has been started. Test UUID: %s";
    private static final String PRODUCER_FINISH_TEST_RESPONSE = "Request received. Test has been finished. Test UUID: %s";
    private static final String PRODUCER_EXCEPTION_FINISH_TEST_RESPONSE = "Request received. Test has been finished with failure. Test UUID: %s. Number of obtained messages: %s";
    private final RestClient restClient = RestClient.create();
    private final TestService testService;

    @PostMapping("/perform")
    public ResponseEntity<RedirectStartTestResponseDto> performTest(@RequestBody TestSettingsDto testSettingDto) {
        new Thread(() -> testService.performTest(testSettingDto)).start();
        return ResponseEntity.ok(RedirectStartTestResponseDto.builder()
                .producerApiResponse(String.format(PRODUCER_START_TEST_RESPONSE, testSettingDto.getTestUUID()))
                .build());
    }

    @PostMapping("/notify")
    public ResponseEntity<Void> notifyProducer(@RequestBody BigDecimal brokerStatusPercentage) {
        TestService.notifyProducer(brokerStatusPercentage);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/finish")
    public ResponseEntity<RedirectFinishTestResponseDto> finishTest(@RequestBody FinishTestDto finishTestDto) {
        boolean isProducerFinishTest = testService.finishTest(finishTestDto);
        RedirectFinishTestResponseDto redirectFinishTestResponseDto = redirectRequestToFinishTest(finishTestDto);
        return ResponseEntity.ok(RedirectFinishTestResponseDto.builder()
                .isProducerFinishTest(isProducerFinishTest)
                .producerApiResponse(getProducerApiResponse(isProducerFinishTest, finishTestDto.getTestUUID()))
                .isConsumerFinishTest(redirectFinishTestResponseDto.isConsumerFinishTest())
                .consumerApiResponse(redirectFinishTestResponseDto.getConsumerApiResponse())
                .build());
    }

    private String getProducerApiResponse(boolean isProducerFinishTest, UUID testUUID) {
        return isProducerFinishTest ?
                String.format(PRODUCER_FINISH_TEST_RESPONSE, testUUID) :
                String.format(PRODUCER_EXCEPTION_FINISH_TEST_RESPONSE, testUUID, TestService.TOTAL_MESSAGES_OBTAINED);
    }

    private RedirectFinishTestResponseDto redirectRequestToFinishTest(Object requestBody) {
        return Optional.ofNullable(restClient.post()
                        .uri(CONSUMER_API_URL_ADDRESS + "/finish")
                        .body(requestBody)
                        .retrieve()
                        .body(RedirectFinishTestResponseDto.class))
                .orElseThrow(() -> new HttpStreamRedirectException(CONSUMER_API_URL_ADDRESS + "/finish"));
    }
}
