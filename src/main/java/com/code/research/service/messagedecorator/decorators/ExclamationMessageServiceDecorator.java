package com.code.research.service.messagedecorator.decorators;

import com.code.research.service.messagedecorator.MessageService;

public class ExclamationMessageServiceDecorator extends MessageServiceDecorator {

    public ExclamationMessageServiceDecorator(MessageService delegate) {
        super(delegate);
    }

    @Override
    public String getMessage() {
        String message = delegate.getMessage();
        return message + "!!!";
    }

}
