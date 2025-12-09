package com.connormorales.weather_dashboard.configuration;



import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${app.rabbit.input-queue}")
    private String inputQueueName;

    @Value("${app.rabbit.output-queue}")
    private String outputQueueName;

    @Value("${app.rabbit.exchange}")
    private String exchangeName;

    @Value("${app.rabbit.input-routing-key}")
    private String inputRoutingKey;

    @Value("${app.rabbit.output-routing-key}")
    private String outputRoutingKey;

    @Bean
    public Queue inputQueue() {
        return new Queue(inputQueueName, true);
    }

    @Bean
    public Queue outputQueue() {
        return new Queue(outputQueueName, true);
    }

    @Bean
    public TopicExchange weatherExchange() {
        return new TopicExchange(exchangeName, true, false);
    }

    @Bean
    public Binding inputBinding(Queue inputQueue, TopicExchange weatherExchange) {
        return BindingBuilder.bind(inputQueue).to(weatherExchange).with(inputRoutingKey);
    }

    @Bean
    public Binding outputBinding(Queue outputQueue, TopicExchange weatherExchange) {
        return BindingBuilder.bind(outputQueue).to(weatherExchange).with(outputRoutingKey);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter converter) {
        RabbitTemplate template = new RabbitTemplate((org.springframework.amqp.rabbit.connection.ConnectionFactory) connectionFactory);
        template.setMessageConverter(converter);
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter converter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory((org.springframework.amqp.rabbit.connection.ConnectionFactory) connectionFactory);
        factory.setMessageConverter(converter);
        return factory;
    }
}
