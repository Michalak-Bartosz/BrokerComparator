package org.message.producer.kafka.controller;

import lombok.RequiredArgsConstructor;
import org.message.producer.kafka.KafkaProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
@RequiredArgsConstructor
public class PayloadController {

    private final KafkaProducer kafkaProducer;

    @GetMapping("/publish/user")
    public ResponseEntity<String> publishUser() {
        kafkaProducer.sendUserRecord();
        return ResponseEntity.ok("User record sent to kafka topic.");
    }

    @GetMapping("/publish/report")
    public ResponseEntity<String> publishReport() {
        kafkaProducer.sendReportRecord();
        return ResponseEntity.ok("Report record sent to kafka topic.");
    }
}
