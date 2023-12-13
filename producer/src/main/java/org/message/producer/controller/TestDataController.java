package org.message.producer.controller;

import lombok.RequiredArgsConstructor;
import org.message.producer.dto.TestSettingsDto;
import org.message.producer.service.TestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestDataController {

    private final TestService testService;

    @PostMapping("/perform")
    public ResponseEntity<String> publishUser(@RequestBody TestSettingsDto testSettingDto) {
        new Thread(() -> testService.performTest(testSettingDto)).start();
        return ResponseEntity.ok("Request received. Test has been started.");
    }
}
