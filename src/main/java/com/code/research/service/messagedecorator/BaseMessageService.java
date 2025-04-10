package com.code.research.service.messagedecorator;

public class BaseMessageService implements MessageService {

    @Override
    public String getMessage() {
        return "Hello from Base Message Service";
    }

}
