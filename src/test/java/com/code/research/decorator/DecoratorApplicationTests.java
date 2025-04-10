package com.code.research.decorator;

import static org.assertj.core.api.Assertions.assertThat;

import com.code.research.service.messagedecorator.BaseMessageService;
import com.code.research.service.messagedecorator.CustomMessageService;
import com.code.research.service.messagedecorator.MessageService;
import com.code.research.service.messagedecorator.decorators.ExclamationMessageServiceDecorator;
import com.code.research.service.messagedecorator.decorators.ReverseMessageServiceDecorator;
import com.code.research.service.messagedecorator.decorators.ReverseWordsMessageServiceDecorator;
import com.code.research.service.messagedecorator.decorators.UpperCaseMessageServiceDecorator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DecoratorApplicationTests {

    @Autowired
    private MessageService decoratedMessageService;

    @Test
    void testPrimaryDecoratorChain() {
        String message = decoratedMessageService.getMessage();
        assertThat(message).isEqualTo("Hello from Base Message Service!!!");
    }

    @Test
    void testUpperCaseThenReverseDecoratorChain() {
        MessageService base = new BaseMessageService();
        MessageService upperCase = new UpperCaseMessageServiceDecorator(base);
        MessageService exclaimed = new ExclamationMessageServiceDecorator(upperCase);
        MessageService reversed = new ReverseWordsMessageServiceDecorator(exclaimed);
        String message = reversed.getMessage();
        assertThat(message).isEqualTo("SERVICE!!! MESSAGE BASE FROM HELLO");
    }

    @Test
    void testCustomMessageDecoratorChain() {
        MessageService custom = new CustomMessageService("Decorator pattern rocks");
        MessageService upperCase = new UpperCaseMessageServiceDecorator(custom);
        MessageService exclaimed = new ExclamationMessageServiceDecorator(upperCase);
        MessageService reversed = new ReverseMessageServiceDecorator(exclaimed);
        String message = reversed.getMessage();
        assertThat(message).isEqualTo("!!!SKCOR NRETTAP ROTAROCED");
    }

}
