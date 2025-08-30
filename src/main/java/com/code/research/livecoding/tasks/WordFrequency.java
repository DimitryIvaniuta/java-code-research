package com.code.research.livecoding.tasks;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@Slf4j
public class WordFrequency {

    private static final Pattern SPLIT = Pattern.compile(
            "[^\\p{L}\\p{N}]+", Pattern.CASE_INSENSITIVE);

    /**
     * Counts case-insensitive word frequencies using Java Streams.
     *
     * @param sentence input text (null/blank allowed)
     * @return Map word -> occurrences (Long)
     */
    public static Map<String, Long> wordFrequencyCount(String sentence) {
        if (sentence == null || sentence.isBlank()) {
            return Collections.emptyMap();
        }
        return SPLIT
                .splitAsStream(sentence.toLowerCase(Locale.ROOT).trim())
                .filter(s -> !s.isEmpty())
                .collect(groupingBy(Function.identity(), LinkedHashMap::new, counting()));
    }

    public static void main(String[] args) {
        String s = """
                To be, or not to be: that is the question
                or Wspaniały świat or Świetny świat.""";
        log.info("Words Count: {}", wordFrequencyCount(s));

    }
}
