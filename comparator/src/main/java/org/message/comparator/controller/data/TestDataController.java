package org.message.comparator.controller.data;

import lombok.RequiredArgsConstructor;
import org.message.comparator.dto.data.FinishTestDto;
import org.message.comparator.dto.data.RedirectFinishTestResponseDto;
import org.message.comparator.dto.data.RedirectStartTestResponseDto;
import org.message.comparator.dto.data.TestSettingsDto;
import org.message.comparator.entity.data.TestReport;
import org.message.comparator.exception.HttpStreamRedirectException;
import org.message.comparator.service.data.TestReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.message.comparator.config.ApiConstants.PRODUCER_API_URL_ADDRESS;
import static org.message.comparator.config.ApiConstants.REQUEST_MAPPING_NAME;

@RestController
@RequestMapping(REQUEST_MAPPING_NAME + "/test")
@RequiredArgsConstructor
public class TestDataController {
    private static final String COMPARE_API_START_TEST_RESPONSE = "Successful redirected message. Test UUID: %s";
    private static final String COMPARE_API_FINISH_TEST_RESPONSE = "Successful redirected message and create test report. Test UUID: %s";
    private static final String COMPARE_API_EXCEPTION_FINISH_TEST_RESPONSE = "Fail redirected message. Test UUID is null!";
    private final RestClient restClient = RestClient.create();
    private final TestReportService reportService;

    @PostMapping("/perform")
    public ResponseEntity<RedirectStartTestResponseDto> performTest(
            @RequestBody TestSettingsDto testSettingsDto,
            @CookieValue(name = "accessTokenCookie", defaultValue = "default-access-token-value") String cookie) {
        UUID testUUID = UUID.randomUUID();
        testSettingsDto.setTestUUID(testUUID);
        RedirectStartTestResponseDto redirectStartTestResponseDto = redirectRequestToStartTest(testSettingsDto);
        redirectStartTestResponseDto.setCompareApiResponse(String.format(COMPARE_API_START_TEST_RESPONSE, testUUID));
        redirectStartTestResponseDto.setTestUUID(testUUID);
        return ResponseEntity.ok(redirectStartTestResponseDto);
    }

    @PostMapping("/finish")
    public ResponseEntity<RedirectFinishTestResponseDto> finishTest(
            @RequestBody FinishTestDto finishTestDto,
            @CookieValue(name = "accessTokenCookie", defaultValue = "default-access-token-value") String cookie) {
        if (ObjectUtils.isEmpty(finishTestDto.getTestUUID())) {
            return ResponseEntity.badRequest()
                    .body(RedirectFinishTestResponseDto.builder()
                            .compareApiResponse(COMPARE_API_EXCEPTION_FINISH_TEST_RESPONSE)
                            .build());
        }
        UUID testUUID = finishTestDto.getTestUUID();
        RedirectFinishTestResponseDto redirectFinishTestResponseDto = redirectRequestToFinishTest(finishTestDto);
        reportService.createTestReport(testUUID);
        redirectFinishTestResponseDto.setTestUUID(testUUID);
        redirectFinishTestResponseDto.setCompareApiResponse(String.format(COMPARE_API_FINISH_TEST_RESPONSE, testUUID));
        return ResponseEntity.ok(redirectFinishTestResponseDto);
    }

    @GetMapping("/report")
    public ResponseEntity<List<TestReport>> getTestReports(
            @CookieValue(name = "accessTokenCookie", defaultValue = "default-access-token-value") String cookie) {
        return ResponseEntity.ok(reportService.getTestReport());
    }

    private RedirectStartTestResponseDto redirectRequestToStartTest(Object requestBody) {
        return Optional.ofNullable(restClient.post()
                        .uri(PRODUCER_API_URL_ADDRESS + "/perform")
                        .body(requestBody)
                        .retrieve()
                        .body(RedirectStartTestResponseDto.class))
                .orElseThrow(() -> new HttpStreamRedirectException(PRODUCER_API_URL_ADDRESS + "/perform"));
    }

    private RedirectFinishTestResponseDto redirectRequestToFinishTest(Object requestBody) {
        return Optional.ofNullable(restClient.post()
                        .uri(PRODUCER_API_URL_ADDRESS + "/finish")
                        .body(requestBody)
                        .retrieve()
                        .body(RedirectFinishTestResponseDto.class))
                .orElseThrow(() -> new HttpStreamRedirectException(PRODUCER_API_URL_ADDRESS + "/finish"));
    }
}
