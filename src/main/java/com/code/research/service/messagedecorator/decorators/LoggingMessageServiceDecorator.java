package com.code.research.service.messagedecorator.decorators;

import com.code.research.service.messagedecorator.MessageService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingMessageServiceDecorator extends MessageServiceDecorator {

    public LoggingMessageServiceDecorator(MessageService delegate) {
        super(delegate);
    }

    @Override
    public String getMessage() {
        String message = delegate.getMessage();
        log.info("LoggingMessageServiceDecorator: Original message: {}", message);
        return message;
    }

}
