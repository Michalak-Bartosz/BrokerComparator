package org.message.producer.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.message.model.util.BrokerType;
import org.message.producer.dto.*;
import org.message.producer.exception.FinishAsyncTestException;
import org.message.producer.exception.HttpStreamRedirectException;
import org.message.producer.service.TestService;
import org.message.producer.util.TestProgressUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.message.producer.config.ApiConstants.CONSUMER_API_URL_ADDRESS;
import static org.message.producer.config.ApiConstants.REQUEST_MAPPING_NAME;

@Slf4j
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
    public ResponseEntity<RedirectStartTestResponseDto> performTest(@RequestBody TestSettingsDto testSettingsDto) {
        TestProgressUtil.setTotalMessagesToSend(calculateTotalMessagesToSend(testSettingsDto));
        TestProgressUtil.setTotalMessagesObtained(0);
        new Thread(() -> testService.performTest(testSettingsDto)).start();
        if (testSettingsDto.isSync()) {
            log.info("➡️🏁 Start synchronized test...");
        } else {
            log.info("🔀🏁 Start asynchronous test...");
            FinishAsyncTestDto finishAsyncTestDto = getFinishAsyncTestDto(testSettingsDto);
            RedirectFinishAsyncTestResponseDto redirectFinishAsyncTestResponseDto = redirectRequestToFinishAsyncTest(finishAsyncTestDto);
            if (!redirectFinishAsyncTestResponseDto.isConsumerFinishTest()) {
                throw new FinishAsyncTestException(redirectFinishAsyncTestResponseDto.getConsumerApiResponse());
            }
            log.info("🔀🏁 Finish asynchronous test!");
        }
        return ResponseEntity.ok(RedirectStartTestResponseDto.builder()
                .producerApiResponse(String.format(PRODUCER_START_TEST_RESPONSE, testSettingsDto.getTestUUID()))
                .build());
    }

    @PostMapping("/notify")
    public ResponseEntity<Void> notifyProducer(@RequestBody BrokerType brokerType) {
        TestService.notifyProducer(brokerType);
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

    private int calculateTotalMessagesToSend(TestSettingsDto testSettingsDto) {
        return testSettingsDto.getNumberOfMessagesToSend() * testSettingsDto.getNumberOfAttempts() * testSettingsDto.getBrokerTypes().size();
    }

    private int calculateMessagesToSendByBroker(TestSettingsDto testSettingsDto) {
        return testSettingsDto.getNumberOfMessagesToSend() * testSettingsDto.getNumberOfAttempts();
    }

    private FinishAsyncTestDto getFinishAsyncTestDto(TestSettingsDto testSettingsDto) {
        return FinishAsyncTestDto.builder()
                .totalNumberOfMessagesInTest(TestProgressUtil.getTotalMessagesToSend())
                .brokerTotalNumberOfMessagesMap(getBrokerTotalNumberOfMessagesMap(testSettingsDto))
                .build();
    }

    private Map<BrokerType, Integer> getBrokerTotalNumberOfMessagesMap(TestSettingsDto testSettingsDto) {
        int numberOfMessagesToSendByBroker = calculateMessagesToSendByBroker(testSettingsDto);
        Map<BrokerType, Integer> brokerTotalNumberOfMessagesMap = new EnumMap<>(BrokerType.class);
        for (BrokerType brokerType : testSettingsDto.getBrokerTypes()) {
            brokerTotalNumberOfMessagesMap.put(brokerType, numberOfMessagesToSendByBroker);
        }
        return brokerTotalNumberOfMessagesMap;
    }

    private String getProducerApiResponse(boolean isProducerFinishTest, UUID testUUID) {
        return isProducerFinishTest ?
                String.format(PRODUCER_FINISH_TEST_RESPONSE, testUUID) :
                String.format(PRODUCER_EXCEPTION_FINISH_TEST_RESPONSE, testUUID, TestProgressUtil.getTotalMessagesObtained());
    }

    private RedirectFinishTestResponseDto redirectRequestToFinishTest(Object requestBody) {
        return Optional.ofNullable(restClient.post()
                        .uri(CONSUMER_API_URL_ADDRESS + "/finish")
                        .body(requestBody)
                        .retrieve()
                        .body(RedirectFinishTestResponseDto.class))
                .orElseThrow(() -> new HttpStreamRedirectException(CONSUMER_API_URL_ADDRESS + "/finish"));
    }

    private RedirectFinishAsyncTestResponseDto redirectRequestToFinishAsyncTest(Object requestBody) {
        return Optional.ofNullable(restClient.post()
                        .uri(CONSUMER_API_URL_ADDRESS + "/finish/async")
                        .body(requestBody)
                        .retrieve()
                        .body(RedirectFinishAsyncTestResponseDto.class))
                .orElseThrow(() -> new HttpStreamRedirectException(CONSUMER_API_URL_ADDRESS + "/finish/async"));
    }
}
