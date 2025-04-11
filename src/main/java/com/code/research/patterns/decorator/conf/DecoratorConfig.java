package com.code.research.patterns.decorator.conf;

import com.code.research.service.messagedecorator.BaseMessageService;
import com.code.research.service.messagedecorator.MessageService;
import com.code.research.service.messagedecorator.decorators.ExclamationMessageServiceDecorator;
import com.code.research.service.messagedecorator.decorators.LoggingMessageServiceDecorator;
import com.code.research.service.messagedecorator.decorators.ReverseMessageServiceDecorator;
import com.code.research.service.messagedecorator.decorators.ReverseWordsMessageServiceDecorator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DecoratorConfig {

    // Base service bean
    @Bean
    public MessageService baseMessageService() {
        return new BaseMessageService();
    }
    // Decorator for reverse words
    @Bean
    public MessageService reverseWordsMessageService(@Qualifier("baseMessageService") MessageService baseMessageService) {
        return new ReverseWordsMessageServiceDecorator(baseMessageService);
    }

    // Decorator for reverse
    @Bean
    public MessageService reverseMessageService(@Qualifier("baseMessageService") MessageService baseMessageService) {
        return new ReverseMessageServiceDecorator(baseMessageService);
    }

    // Decorator for logging
    @Bean
    public MessageService loggingMessageService(@Qualifier("baseMessageService") MessageService baseMessageService) {
        return new LoggingMessageServiceDecorator(baseMessageService);
    }

    // Primary decorated bean which appends exclamation marks.
    @Bean
    @Primary
    public MessageService decoratedMessageService(MessageService loggingMessageService) {
        return new ExclamationMessageServiceDecorator(loggingMessageService);
    }

}