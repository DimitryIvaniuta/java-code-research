package com.code.research.algorithm;

import java.util.*;

/**
 * Tiny "pg_trgm-like" trigram search:
 * - Build trigrams from " " + text + " " (pads once on both ends)
 * - Similarity = Dice coefficient: 2 * |A∩B| / (|A| + |B|)
 * - Search: rank candidates by similarity, optional threshold
 */
public final class TrigramSearch {

    /**
     * Build trigrams like Postgres: pad once on both ends and slide window size=3
     */
    public static List<String> trigrams(String s) {
        if (s == null) s = "";
        String t = (" " + s.toLowerCase(Locale.ROOT) + " "); // simple normalization + padding
        if (t.length() < 3) return List.of(t);               // degenerate case: single trigram
        List<String> out = new ArrayList<>(Math.max(1, t.length() - 2));
        for (int i = 0; i + 3 <= t.length(); i++) {
            out.add(t.substring(i, i + 3));
        }
        return out;
    }

    /**
     * Dice similarity: 2*|A∩B| / (|A| + |B|). Returns 0..1.
     */
    public static double similarity(String a, String b) {
        List<String> ta = trigrams(a);
        List<String> tb = trigrams(b);
        if (ta.isEmpty() && tb.isEmpty()) return 1.0;

        // Multiset intersection (counts matter): use maps of trigram -> count
        Map<String, Integer> ca = countMap(ta);
        Map<String, Integer> cb = countMap(tb);

        int inter = 0, na = ta.size(), nb = tb.size();
        for (var e : ca.entrySet()) {
            Integer bCnt = cb.get(e.getKey());
            if (bCnt != null) inter += Math.min(e.getValue(), bCnt);
        }
        return (na + nb == 0) ? 0.0 : (2.0 * inter) / (na + nb);
    }

    private static Map<String, Integer> countMap(List<String> xs) {
        Map<String, Integer> m = new HashMap<>();
        for (String x : xs) m.merge(x, 1, Integer::sum);
        return m;
    }

    /**
     * Rank candidates by similarity to query (desc), keep those >= threshold
     */
    public static List<Result> search(String query, List<String> candidates, double threshold) {
        List<Result> res = new ArrayList<>();
        for (String c : candidates) {
            double sim = similarity(query, c);
            if (sim >= threshold) res.add(new Result(c, sim));
        }
        res.sort((p, q) -> {
            int cmp = Double.compare(q.score, p.score);     // higher score first
            return (cmp != 0) ? cmp : p.text.compareTo(q.text);
        });
        return res;
    }

    public static final class Result {
        public final String text;
        public final double score;

        public Result(String text, double score) {
            this.text = text;
            this.score = score;
        }

        @Override
        public String toString() {
            return String.format(Locale.ROOT, "%.3f  %s", score, text);
        }
    }

    // --- tiny demo ---
    public static void main(String[] args) {
        // Show trigrams like the prompt example
        System.out.println(trigrams("hello")); // [ hel, hel, ell, llo, lo ]

        // Pairwise similarity
        System.out.println(similarity("hello", "hullo"));   // close (~0.67)
        System.out.println(similarity("hello", "yellow"));  // also related
        System.out.println(similarity("hello", "world"));   // low

        // Fuzzy search
        List<String> dict = List.of("hello", "hullo", "hallo", "yellow", "hero", "world", "hell");
        for (Result r : search("hello", dict, 0.3)) {
            System.out.println(r);
        }
    }
}
