package com.code.research.hash.digitstatistics;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class FrequencyWordsCounterProcessorApplication {
    
    public static void main(String[] args) {
        String[] words = {"apple", "banana", "apple", "orange", "banana", "apple"};

        Map<String, Integer> mergeResult = FrequencyWordsCounterProcessor.frequencyUsingMerge(words);
        Map<String, Integer> computeIfAbsentResult = FrequencyWordsCounterProcessor.frequencyUsingComputeIfAbsent(words);
        Map<String, Integer> putOrDefaultResult = FrequencyWordsCounterProcessor.frequencyUsingPutOrDefault(words);
        Map<String, Integer> computeResult = FrequencyWordsCounterProcessor.frequencyUsingCompute(words);

        log.info("Frequency using merge:          " + mergeResult);
        log.info("Frequency using computeIfAbsent:" + computeIfAbsentResult);
        log.info("Frequency using putOrDefault:   " + putOrDefaultResult);
        log.info("Frequency using compute:        " + computeResult);
    }

}
