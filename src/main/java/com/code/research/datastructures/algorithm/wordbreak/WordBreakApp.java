package com.code.research.datastructures.algorithm.wordbreak;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.code.research.datastructures.algorithm.wordbreak.WordBreak.wordBreak;
import static com.code.research.datastructures.algorithm.wordbreak.WordBreak.wordBreakAll;

@Slf4j
public class WordBreakApp {
    public static void main(String[] args) {
        Set<String> dict = new HashSet<>(Arrays.asList(
                "i", "like", "sam", "sung", "samsung", "mobile",
                "ice", "cream", "icecream", "man", "go", "mango"
        ));

        String[] inputs = { "ilike", "ilikesamsung", "ilikeapple" };

        for (String s : inputs) {
            log.info("Input: \"{}\"", s);
            boolean can = wordBreak(s, dict);
            log.info("  Can segment? {}", (can ? "Yes" : "No"));
            if (can) {
                List<String> segs = wordBreakAll(s, dict);
                log.info("  Segmentations:");
                for (String sentence : segs) {
                    log.info("    • {}", sentence);
                }
            }
            log.info("");
        }
    }
}
