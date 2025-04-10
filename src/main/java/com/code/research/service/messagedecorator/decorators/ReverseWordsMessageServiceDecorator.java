package com.code.research.service.messagedecorator.decorators;

import com.code.research.service.messagedecorator.MessageService;

import java.util.Arrays;
import java.util.List;
import java.util.Collections;

public class ReverseWordsMessageServiceDecorator extends MessageServiceDecorator {

    public ReverseWordsMessageServiceDecorator(MessageService delegate) {
        super(delegate);
    }

    @Override
    public String getMessage() {
        // Get the message from the delegate
        String message = delegate.getMessage();
        // Split the message into words (split by one or more whitespace characters)
        List<String> words = Arrays.asList(message.split("\\s+"));
        // Reverse the order of words
        Collections.reverse(words);
        // Rejoin the words into a single string with a single space between them
        return String.join(" ", words);
    }

}
