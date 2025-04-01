package com.code.research.hash.digitstatistics;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FrequencyCounterProcessorApplication {
    
    public static void main(String[] args) {
        int number = 2232423;
        log.info("Input Number: " + number);

        FrequencyCounter mergeCounter = FrequencyCounterProcessor::countUsingMerge;
        FrequencyCounter computeIfAbsentCounter = FrequencyCounterProcessor::countUsingComputeIfAbsent;
        FrequencyCounter putOrDefaultCounter = FrequencyCounterProcessor::countUsingPutOrDefault;
        FrequencyCounter computeCounter = FrequencyCounterProcessor::countUsingCompute;

        log.info("Frequency using merge():           {}", mergeCounter.countFrequency(number));
        log.info("Frequency using computeIfAbsent(): {}", computeIfAbsentCounter.countFrequency(number));
        log.info("Frequency using putOrDefault():    {}", putOrDefaultCounter.countFrequency(number));
        log.info("Frequency using compute():         {}", computeCounter.countFrequency(number));

    }
    
}
