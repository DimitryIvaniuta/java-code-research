package com.code.research.service.messagedecorator.decorators;

import com.code.research.service.messagedecorator.MessageService;

public class ReverseMessageServiceDecorator extends MessageServiceDecorator {

    public ReverseMessageServiceDecorator(MessageService delegate) {
        super(delegate);
    }

    @Override
    public String getMessage() {
        String message = delegate.getMessage();
        return new StringBuilder(message).reverse().toString();
    }

}
