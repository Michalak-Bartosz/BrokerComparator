package org.message.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.message.producer.model.User;
import org.message.producer.util.RandomUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.message.producer.util.KafkaMessageUtil.getKafkaProducerRecord;

@Slf4j
@SpringBootApplication
public class ProducerApplication {

    public static void main(String[] args) throws JsonProcessingException {
        User user = RandomUtil.generateUser();
        var kafkaRecord = getKafkaProducerRecord("user-topic", user);

//        ObjectMapper ob = new ObjectMapper();
        log.info(kafkaRecord.toString());
        SpringApplication.run(ProducerApplication.class, args);
    }

}
