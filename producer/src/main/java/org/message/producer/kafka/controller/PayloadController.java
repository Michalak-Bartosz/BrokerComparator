package org.message.producer.kafka.controller;

import lombok.RequiredArgsConstructor;
import org.message.producer.dto.TestSettingDto;
import org.message.producer.kafka.KafkaProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class PayloadController {

    private final KafkaProducer kafkaProducer;

    @PostMapping("/perform")
    public ResponseEntity<String> publishUser(@RequestBody TestSettingDto testSettingDto) {
        kafkaProducer.startTest(testSettingDto);
        return ResponseEntity.ok("User record sent to kafka topic.");
    }
}
