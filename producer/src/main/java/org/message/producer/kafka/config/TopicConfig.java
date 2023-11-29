package org.message.producer.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfig {

    @Value("${spring.kafka.topic.identification-data-topic}")
    private String identificationTopicName;

    @Bean
    public NewTopic createTopic() {
        return TopicBuilder.name(identificationTopicName)
                .build();
    }
}
