package org.message.producer.rabbitmq.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RabbitMqConfig {

    @Value("${spring.rabbitmq.queue.user}")
    private String userQueueName;
    @Value("${spring.rabbitmq.queue.report}")
    private String reportQueueName;
    @Value("${spring.rabbitmq.queue.debug-info}")
    private String debugInfoQueueName;

    @Value("${spring.rabbitmq.routing.key.user}")
    private String userQueueRoutingKey;
    @Value("${spring.rabbitmq.routing.key.report}")
    private String reportQueueRoutingKey;
    @Value("${spring.rabbitmq.routing.key.debug-info}")
    private String debugInfoQueueRoutingKey;

    @Value("${spring.rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.virtual-host}")
    private String vHost;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;
    @Value("${spring.rabbitmq.port}")
    private int port;

    @Bean
    public Queue userQueue() {
        return new Queue(userQueueName, true, false, false);
    }

    @Bean
    public Queue reportQueue() {
        return new Queue(reportQueueName, true, false, false);
    }

    @Bean
    public Queue debugInfoQueue() {
        return new Queue(debugInfoQueueName, true, false, false);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchangeName, true, false);
    }

    @Bean
    public Binding userQueueBinding(TopicExchange exchange) {
        return BindingBuilder.bind(userQueue()).to(exchange).with(userQueueRoutingKey);
    }

    @Bean
    public Binding reportQueueBinding(TopicExchange exchange) {
        return BindingBuilder.bind(reportQueue()).to(exchange).with(reportQueueRoutingKey);
    }

    @Bean
    public Binding debugInfoQueueBinding(TopicExchange exchange) {
        return BindingBuilder.bind(debugInfoQueue()).to(exchange).with(debugInfoQueueRoutingKey);
    }

    @Bean
    public MessageConverter converter() {
        ObjectMapper objectMapper = new JsonMapper().registerModule(new JavaTimeModule());
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost(host);
        factory.setVirtualHost(vHost);
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setPort(port);
        log.info("RabbitMQ host in [{}] at port [{}]", host, port);
        return factory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(TopicExchange exchange) {
        RabbitAdmin admin = new RabbitAdmin(connectionFactory());
        admin.declareExchange(exchange);
        return admin;
    }
}
