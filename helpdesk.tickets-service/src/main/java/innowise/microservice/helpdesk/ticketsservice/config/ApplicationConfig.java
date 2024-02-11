package innowise.microservice.helpdesk.ticketsservice.config;

import innowise.microservice.helpdesk.ticketsservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static innowise.microservice.helpdesk.ticketsservice.util.Constants.EMAIL_EXCHANGE;
import static innowise.microservice.helpdesk.ticketsservice.util.Constants.EMAIL_QUEUE;
import static innowise.microservice.helpdesk.ticketsservice.util.Constants.EMAIL_ROUTING_KEY;
import static innowise.microservice.helpdesk.ticketsservice.util.Constants.EXCHANGE;
import static innowise.microservice.helpdesk.ticketsservice.util.Constants.FEEDBACK_EXCHANGE;
import static innowise.microservice.helpdesk.ticketsservice.util.Constants.FEEDBACK_QUEUE;
import static innowise.microservice.helpdesk.ticketsservice.util.Constants.FEEDBACK_ROUTING_KEY;
import static innowise.microservice.helpdesk.ticketsservice.util.Constants.QUEUE;
import static innowise.microservice.helpdesk.ticketsservice.util.Constants.ROUTING_KEY;


@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository repository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public Queue queue() {
        return new Queue(QUEUE);
    }
    @Bean
    public Queue emailQueue() {
        return new Queue(EMAIL_QUEUE);
    }
    @Bean
    public Queue emailFeedbackQueue() {
        return new Queue(FEEDBACK_QUEUE);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
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
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(ROUTING_KEY);
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
