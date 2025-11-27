package com.code.research.algorithm;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Must-know live-coding patterns with **SortedMap (TreeMap)** and **SortedSet (TreeSet)**.
 * Short, obvious, and interview-ready with comments.
 */
public final class SortedCollectionsLiveCoding {

    /* -------------------------------------------------------------
     * 1) Unique + sorted (ascending) using TreeSet
     * ------------------------------------------------------------- */
    public static int[] dedupeAndSort(int[] a) {
        // TreeSet keeps elements UNIQUE and in NATURAL ORDER
        TreeSet<Integer> set = new TreeSet<>();
        for (int x : a) set.add(x);
        // Convert back to primitive int[]
        return set.stream().mapToInt(Integer::intValue).toArray();
    }

    /* -------------------------------------------------------------
     * 2) Unique + sorted (descending) using TreeSet#descendingSet
     * ------------------------------------------------------------- */
    public static int[] dedupeAndSortDesc(int[] a) {
        TreeSet<Integer> set = new TreeSet<>();
        for (int x : a) set.add(x);
        return set.descendingSet().stream().mapToInt(Integer::intValue).toArray();
    }

    /* -------------------------------------------------------------
     * 3) Range query with TreeSet: count DISTINCT values in [lo, hi]
     *    Uses NavigableSet#subSet(from, inclusive, to, inclusive)
     * ------------------------------------------------------------- */
    public static int rangeDistinctCount(int[] a, int lo, int hi) {
        TreeSet<Integer> set = new TreeSet<>();
        for (int x : a) set.add(x);
        return set.subSet(lo, true, hi, true).size();
    }

    /* -------------------------------------------------------------
     * 4) Word frequency sorted by WORD (lexicographic) using TreeMap
     * ------------------------------------------------------------- */
    public static Map<String, Integer> wordFreqSorted(String text) {
        TreeMap<String, Integer> map = new TreeMap<>();      // sorted by key
        for (String w : text.toLowerCase().split("[^a-z0-9]+")) {
            if (!w.isEmpty()) map.merge(w, 1, Integer::sum); // count words
        }
        return map; // already sorted by word
    }

    /* -------------------------------------------------------------
     * 5) Top-K frequent words using TreeMap as "freq → set of words"
     *    TreeMap with descendingKeySet() gives highest freq first.
     *    Words at same freq kept sorted via TreeSet.
     * ------------------------------------------------------------- */
    public static List<String> topKFrequentWords(String text, int k) {
        // raw frequency (unsorted)
        Map<String, Integer> f = new HashMap<>();
        for (String w : text.toLowerCase().split("[^a-z0-9]+"))
            if (!w.isEmpty()) f.merge(w, 1, Integer::sum);

        // freq → sorted words (TreeSet)
        TreeMap<Integer, TreeSet<String>> byFreq = new TreeMap<>();
        for (var e : f.entrySet())
            byFreq.computeIfAbsent(e.getValue(),( fk )-> new TreeSet<>()).add(e.getKey());

        // iterate frequencies from high to low; collect up to k words
        List<String> out = new ArrayList<>(k);
        for (Integer freq : byFreq.descendingKeySet()) {
            for (String w : byFreq.get(freq)) {
                out.add(w);
                if (out.size() == k) return out;
            }
        }
        return out;
    }

    /* -------------------------------------------------------------
     * 6) Calendar booking without overlaps (LeetCode 729: MyCalendar I)
     *    TreeMap<start, end>; to book [s,e), check nearest neighbors.
     * ------------------------------------------------------------- */
    public static final class MyCalendar {
        private final TreeMap<Integer,Integer> map = new TreeMap<>(); // start → end

        public boolean book(int start, int end) {
            // Check previous event (floorKey) and next event (ceilingKey)
            Map.Entry<Integer,Integer> prev = map.floorEntry(start);
            if (prev != null && prev.getValue() > start) return false; // overlaps previous
            Map.Entry<Integer,Integer> next = map.ceilingEntry(start);
            if (next != null && next.getKey() < end) return false;     // overlaps next
            map.put(start, end);                                       // no overlap → book it
            return true;
        }
    }

    /* -------------------------------------------------------------
     * 7) Merge intervals using TreeMap (sorted by start)
     *    Input intervals as [start, end] (closed or half-open works similarly).
     * ------------------------------------------------------------- */
    public static int[][] mergeIntervals(int[][] intervals) {
        if (intervals.length == 0) return new int[0][0];
        // Put into TreeMap to sort by start (keep largest end for same start)
        TreeMap<Integer,Integer> tm = new TreeMap<>();
        for (int[] it : intervals) tm.merge(it[0], it[1], Math::max);

        List<int[]> res = new ArrayList<>();
        int curS = -1, curE = -1;
        for (var e : tm.entrySet()) {
            int s = e.getKey(), eEnd = e.getValue();
            if (curS == -1) { curS = s; curE = eEnd; }           // start first segment
            else if (s <= curE) { curE = Math.max(curE, eEnd); } // overlap → extend
            else { res.add(new int[]{curS, curE}); curS = s; curE = eEnd; } // disjoint → push
        }
        res.add(new int[]{curS, curE});
        return res.toArray(new int[0][]);
    }

    /* -------------------------------------------------------------
     * 8) Sweep-line with TreeMap (events): max concurrent intervals
     *    For each [s,e) do +1 at s, -1 at e. Prefix sum over sorted keys.
     * ------------------------------------------------------------- */
    public static int maxConcurrent(int[][] intervals) {
        TreeMap<Integer,Integer> diff = new TreeMap<>();
        for (int[] it : intervals) {
            diff.merge(it[0], 1, Integer::sum);
            diff.merge(it[1], -1, Integer::sum);
        }
        int cur = 0, best = 0;
        for (int delta : diff.values()) { cur += delta; best = Math.max(best, cur); }
        return best;
    }

    /* -------------------------------------------------------------
     * 9) Next greater-or-equal using TreeSet: floor/ceiling demo
     *    Returns the smallest value ≥ x in the set (or empty Optional).
     * ------------------------------------------------------------- */
    public static Optional<Integer> ceilingInSet(int[] a, int x) {
        TreeSet<Integer> set = new TreeSet<>();
        for (int v : a) set.add(v);
        Integer c = set.ceiling(x); // NavigableSet operation
        return Optional.ofNullable(c);
    }

    /* -------------------------------------------------------------
     * 10) Rank board (sum of top-K scores) with a SortedMap multiset
     *     TreeMap<score, count> supports log-time add/remove and descending traversal.
     * ------------------------------------------------------------- */
    public static final class Leaderboard {
        private final Map<Integer,Integer> scores = new HashMap<>();          // id → score
        private final TreeMap<Integer,Integer> bag = new TreeMap<>();         // score → multiplicity

        public void addScore(int id, int delta) {
            int old = scores.getOrDefault(id, 0);
            if (old != 0) dec(old);                                           // remove old score from multiset
            int now = old + delta;
            scores.put(id, now);
            inc(now);                                                         // add new score
        }
        public int top(int k) {
            int sum = 0, need = k;
            for (var e : bag.descendingMap().entrySet()) {                    // high → low
                int score = e.getKey(), cnt = e.getValue();
                int take = Math.min(need, cnt);
                sum += score * take;
                need -= take;
                if (need == 0) break;
            }
            return sum;
        }
        public void reset(int id) {
            int sc = scores.getOrDefault(id, 0);
            if (sc != 0) { dec(sc); scores.put(id, 0); }
        }
        private void inc(int score) { bag.merge(score, 1, Integer::sum); }
        private void dec(int score) {
            bag.compute(score, (k,v) -> (v == 1) ? null : v - 1);
        }
    }

    /* ------------------------------- demo ------------------------------- */
    public static void main(String[] args) {
        System.out.println(Arrays.toString(dedupeAndSort(new int[]{4,2,2,5,1})));       // [1,2,4,5]
        System.out.println(Arrays.toString(dedupeAndSortDesc(new int[]{4,2,2,5,1})));   // [5,4,2,1]
        System.out.println(rangeDistinctCount(new int[]{1,2,2,3,10,11}, 2, 10));        // 3 (2,3,10)

        System.out.println(wordFreqSorted("To be, or not to be.")); // {be=2, not=1, or=1, to=2}
        System.out.println(topKFrequentWords("a a a b b c c c c d", 2)); // [c, a] (example)

        MyCalendar cal = new MyCalendar();
        System.out.println(cal.book(10, 20)); // true
        System.out.println(cal.book(15, 25)); // false (overlap)
        System.out.println(cal.book(20, 30)); // true

        int[][] merged = mergeIntervals(new int[][]{{1,3},{2,6},{8,10},{15,18}});
        System.out.println(Arrays.stream(merged).map(Arrays::toString).collect(Collectors.toList())); // [[1, 6], [8, 10], [15, 18]]

        System.out.println(maxConcurrent(new int[][]{{10,20},{15,25},{20,30}})); // 2

        System.out.println(ceilingInSet(new int[]{1,3,5,7}, 6)); // Optional[7]
        System.out.println(ceilingInSet(new int[]{1,3,5,7}, 8)); // Optional.empty

        Leaderboard lb = new Leaderboard();
        lb.addScore(1, 50); lb.addScore(2, 30); lb.addScore(3, 40); lb.addScore(2, 25); // id2 -> 55
        System.out.println(lb.top(2)); // 105 (55 + 50)
        lb.reset(2);
        System.out.println(lb.top(2)); // 90  (50 + 40)
    }
}
