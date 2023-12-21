package org.message.consumer.rabbitmq.config;

import lombok.RequiredArgsConstructor;
import org.message.consumer.rabbitmq.RabbitMqDebugInfoConsumer;
import org.message.consumer.rabbitmq.RabbitMqReportConsumer;
import org.message.consumer.rabbitmq.RabbitMqUserConsumer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@Configuration
@RequiredArgsConstructor
public class RabbitMqConfig {
    private final RabbitMqUserConsumer rabbitMqUserConsumer;
    private final RabbitMqReportConsumer rabbitMqReportConsumer;
    private final RabbitMqDebugInfoConsumer rabbitMqDebugInfoConsumer;

    @Bean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor(); // Or use another one of your liking
    }

    @Bean
    public CommandLineRunner schedulingRunner(TaskExecutor executor) {
        return args -> {
            executor.execute(rabbitMqUserConsumer::receiveUser);
            executor.execute(rabbitMqReportConsumer::receiveReports);
            executor.execute(rabbitMqDebugInfoConsumer::receiveDebugInfo);
        };
    }
}
