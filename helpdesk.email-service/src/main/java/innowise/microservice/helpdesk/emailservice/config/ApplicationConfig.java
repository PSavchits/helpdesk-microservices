package innowise.microservice.helpdesk.emailservice.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static innowise.microservice.helpdesk.emailservice.utils.Constants.EMAIL_EXCHANGE;
import static innowise.microservice.helpdesk.emailservice.utils.Constants.EMAIL_ROUTING_KEY;
import static innowise.microservice.helpdesk.emailservice.utils.Constants.EMAIL_QUEUE;
import static innowise.microservice.helpdesk.emailservice.utils.Constants.FEEDBACK_EXCHANGE;
import static innowise.microservice.helpdesk.emailservice.utils.Constants.FEEDBACK_QUEUE;
import static innowise.microservice.helpdesk.emailservice.utils.Constants.FEEDBACK_ROUTING_KEY;

@Configuration
public class ApplicationConfig {

    @Bean
    public Queue emailQueue() {
        return new Queue(EMAIL_QUEUE);
    }

    @Bean
    public Queue emailFeedbackQueue() {
        return new Queue(FEEDBACK_QUEUE);
    }

    @Bean
    public TopicExchange emailExchange() {
        return new TopicExchange(EMAIL_EXCHANGE);
    }

    @Bean
    public TopicExchange emailFeedbackExchange() {
        return new TopicExchange(FEEDBACK_EXCHANGE);
    }

    @Bean
    public Binding emailBinding(Queue emailQueue, TopicExchange emailExchange) {
        return BindingBuilder
                .bind(emailQueue)
                .to(emailExchange)
                .with(EMAIL_ROUTING_KEY);
    }
    @Bean
    public Binding emailFeedbackBinding(Queue emailFeedbackQueue, TopicExchange emailFeedbackExchange) {
        return BindingBuilder
                .bind(emailFeedbackQueue)
                .to(emailFeedbackExchange)
                .with(FEEDBACK_ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
