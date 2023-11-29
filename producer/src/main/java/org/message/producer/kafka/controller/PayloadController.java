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

    @GetMapping("/publish")
    public ResponseEntity<String> publish() {
        kafkaProducer.sendMessage();
        return ResponseEntity.ok("Json message sent to kafka topic.");
    }
}
