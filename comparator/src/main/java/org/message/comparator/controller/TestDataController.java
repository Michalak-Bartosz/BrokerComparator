package org.message.comparator.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.message.comparator.dto.RedirectResponse;
import org.message.comparator.dto.TestSettingsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.UUID;

import static org.message.comparator.config.ApiConstants.PRODUCER_API_URL_ADDRESS;
import static org.message.comparator.config.ApiConstants.REQUEST_MAPPING_NAME;

@RestController
@RequestMapping(REQUEST_MAPPING_NAME + "/test")
public class TestDataController {
    private static final String COMPARE_API_RESPONSE_PREFIX = "Successful redirected message. Test UUID: ";
    private static final String PRODUCER_API_RESPONSE_PREFIX = "Response message from producer: ";
    private final RestClient restClient = RestClient.create();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/perform")
    public ResponseEntity<String> sendTestSettingsToProducerApp(
            @RequestBody TestSettingsDto testSettingsDto,
            @CookieValue(name = "accessTokenCookie", defaultValue = "default-access-token-value") String cookie) throws JsonProcessingException {
        UUID testUUID = UUID.randomUUID();
        testSettingsDto.setTestUUID(testUUID);
        String responseMessageFromProducer = redirectRequestToProducer(testSettingsDto);
        return ResponseEntity.ok(getRedirectResponseJsonString(responseMessageFromProducer, testUUID));
    }

    private String redirectRequestToProducer(TestSettingsDto testSettingsDto) {
        return restClient.post()
                .uri(PRODUCER_API_URL_ADDRESS)
                .body(testSettingsDto)
                .retrieve()
                .body(String.class);
    }

    private String getRedirectResponseJsonString(String responseMessageFromProducer, UUID testUUID) throws JsonProcessingException {
        return objectMapper.writeValueAsString(RedirectResponse.builder()
                .compareApiResponse(COMPARE_API_RESPONSE_PREFIX + testUUID)
                .producerApiResponse(PRODUCER_API_RESPONSE_PREFIX + responseMessageFromProducer)
                .testUUID(testUUID)
                .build());
    }
}
