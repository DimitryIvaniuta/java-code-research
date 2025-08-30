package com.code.research.livecoding.tasks;

import lombok.extern.slf4j.Slf4j;
import java.text.Normalizer;
import java.util.*;

@Slf4j
public class AnagramsByCodePoint {

    public static List<List<String>> groupAnagramsByCodePoint(List<String> words) {
        if (words == null || words.isEmpty()) {
            return List.of();
        }
        Map<String, List<String>> groups = new LinkedHashMap<>();
        for (String word : words) {
            if (word == null) {
                continue;
            }
            String key = normalizeKeyCodePoint(word);
            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(word);
        }
        return new ArrayList<>(groups.values());
    }

    public static String normalizeKeyCodePoint2(String input) {
        String inputLower = input.toLowerCase(Locale.ROOT);
        int[] cps = inputLower.codePoints().sorted().toArray();
        return new String(cps, 0, cps.length);
    }

    public static String normalizeKeyCodePoint(String input) {
        String nfc = Normalizer.normalize(input, Normalizer.Form.NFC)
                .toLowerCase(Locale.ROOT);
        int[] cps = nfc.codePoints().sorted().toArray();
        StringBuilder sb = new StringBuilder(cps.length);
        for (int cp : cps) {
            sb.appendCodePoint(cp);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        List<String> in = List.of("eat", "tea", "tan", "ate", "nat", "bat", "a😀b", "b😀a");
        log.info("Result lists: {}", groupAnagramsByCodePoint(in));
        // [[eat, tea, ate], [tan, nat], [bat], [a😀b, b😀a]]
    }
}
