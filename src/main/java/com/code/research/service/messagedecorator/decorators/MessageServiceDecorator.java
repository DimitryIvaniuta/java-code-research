package com.code.research.service.messagedecorator.decorators;

import com.code.research.service.messagedecorator.MessageService;

public abstract class MessageServiceDecorator implements MessageService {

    protected final MessageService delegate;

    protected MessageServiceDecorator(MessageService delegate) {
        this.delegate = delegate;
    }

}
