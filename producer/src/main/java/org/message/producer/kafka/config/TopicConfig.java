package org.message.producer.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfig {

    public static final String TOPIC_NAME = "identification-data-topic";

    @Bean
    public NewTopic createTopic() {
        return TopicBuilder.name(TOPIC_NAME)
                .build();
    }
}
