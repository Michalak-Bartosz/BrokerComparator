package org.message.producer.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class TopicConfig {

    @Value("${spring.kafka.topic.user-data-topic}")
    private String userDataTopic;
    @Value("${spring.kafka.topic.report-data-topic}")
    private String reportDataTopic;
    @Value("${spring.kafka.topic.debug-info-data-topic}")
    private String debugInfoDataTopic;


    private NewTopic createTopic(String topicName) {
        return TopicBuilder.name(topicName)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean // This will add the topics to the broker if not present
    public KafkaAdmin.NewTopics topics() {
        return new KafkaAdmin.NewTopics(
                createTopic(userDataTopic),
                createTopic(reportDataTopic),
                createTopic(debugInfoDataTopic));
    }

}
