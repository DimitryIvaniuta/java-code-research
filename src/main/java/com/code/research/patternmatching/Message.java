package com.code.research.patternmatching;

public sealed interface Message permits TextMessage, ImageMessage, VideoMessage {
}
