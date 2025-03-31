package com.code.research.patternmatching;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageProcessor {

    /**
     * Processes a message using a pattern-matching switch.
     *
     * <p>This method uses Java 21's pattern matching feature for switch expressions to
     * differentiate between different message types, extract their properties, and then
     * process them accordingly.
     *
     * @param message the message to process.
     */
    public void processMessage(Message message) {
        String result = switch (message) {
            case TextMessage(String text) -> "Processing Text Message: " + text;
            case ImageMessage(String url, int width, int height) ->
                    "Processing Image Message: URL = " + url + ", dimensions = " + width + "x" + height;
            case VideoMessage(String url, double duration) ->
                    "Processing Video Message: URL = " + url + ", duration = " + duration + " seconds";
            // Although the sealed interface ensures all subtypes are handled, we add a default case.
            default -> throw new IllegalStateException("Unexpected message type: " + message);
        };
        log.info(result);
    }

    public static void main(String[] args) {
        MessageProcessor processor = new MessageProcessor();
        // Create an array of messages.
        Message[] messages = {
                new TextMessage("Hello, world!"),
                new ImageMessage("https://example.com/image.jpg", 800, 600),
                new VideoMessage("https://example.com/video.mp4", 120.5)
        };

        // Process each message.
        for (Message msg : messages) {
            processor.processMessage(msg);
        }
    }
}
