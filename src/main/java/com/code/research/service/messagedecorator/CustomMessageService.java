package com.code.research.service.messagedecorator;

public class CustomMessageService implements MessageService {

    private final String customMessage;

    public CustomMessageService(String customMessage) {
        this.customMessage = customMessage;
    }

    @Override
    public String getMessage() {
        return this.customMessage;
    }

}
