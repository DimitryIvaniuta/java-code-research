package com.code.research.algorithm;

import java.util.*;
import java.util.function.Function;

public final class MapsLiveCoding {

    /* 1) Char frequency (classic getOrDefault) */
    public static Map<Character, Integer> charFreq(String s) {
        Map<Character, Integer> f = new HashMap<>();
        for (char c : s.toCharArray()) {
            f.put(c, f.getOrDefault(c, 0) + 1);

        }
        return f;
    }

    /* 2) Word frequency (split + merge) */
    public static Map<String, Integer> wordFreq(String text) {
        Map<String, Integer> f = new HashMap<>();
        for (String w : text.toLowerCase().split("[^a-z0-9]+")) {
            if (!w.isEmpty()) {
                f.merge(w, 1, Integer::sum);
            }
        }
        return f;
    }

    /* 3) Two Sum — O(n) using value→index map */
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> pos = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            Integer j = pos.get(target - nums[i]);
            if (j != null) {
                return new int[]{j, i};
            }
            pos.put(nums[i], i);
        }
        return new int[0];
    }

    /* 4) Group Anagrams — key by sorted letters */
    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> g = new HashMap<>();
        for (String s : strs) {
            char[] a = s.toCharArray();
            Arrays.sort(a);
            g.computeIfAbsent(
                            new String(a),
                            k -> new ArrayList<>())
                    .add(s);
        }
        return new ArrayList<>(g.values());
    }

    /* 5) First unique character index — LinkedHashMap preserves order */
    public static int firstUniqChar(String s) {
        Map<Character, Integer> f = new LinkedHashMap<>();
        for (char c : s.toCharArray()) {
            f.put(c, f.getOrDefault(c, 0) + 1);
        }
        int idx = 0;
        for (char c : s.toCharArray()) {
            if (f.get(c) == 1) {
                return idx;
            }
            idx++;
        }
        return -1;
    }

    /* 6) Top-K frequent integers — map + heap */
    public static List<Integer> topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> f = new HashMap<>();
        for (int x : nums) {
            f.merge(x, 1, Integer::sum);
        }
        PriorityQueue<Map.Entry<Integer, Integer>> pq =
                new PriorityQueue<>(
                        Comparator.comparingInt(
                                Map.Entry::getValue)
                ); // min-heap by freq
        for (var e : f.entrySet()) {
            pq.offer(e);
            if (pq.size() > k) {
                pq.poll();
            }
        }
        List<Integer> out = new ArrayList<>();
        while (!pq.isEmpty()) {
            out.add(pq.poll().getKey());
        }
        Collections.reverse(out);
        return out;
    }

    /* 7) Isomorphic Strings — two-directional maps */
    public static boolean isIsomorphic(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        Map<Character, Character> st = new HashMap<>();
        Map<Character, Character> ts = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char a = s.charAt(i);
            char b = t.charAt(i);
            if (st.putIfAbsent(a, b) != null && st.get(a) != b) {
                return false;
            }
            if (ts.putIfAbsent(b, a) != null && ts.get(b) != a) {
                return false;
            }
        }
        return true;
    }

    /* 8) Roman to Integer — map lookups */
    public static int romanToInt(String s) {
        Map<Character, Integer> val = Map.of(
                'I', 1, 'V', 5, 'X', 10, 'L', 50,
                'C', 100, 'D', 200, 'M', 1000 // D is 500; typo fixed next line
        );
        // fix mapping with a small builder to keep code short and correct
        val = new HashMap<>(val);
        ((HashMap<Character, Integer>) val).put('D', 500);

        int sum = 0;
        for (int i = 0; i < s.length(); i++) {
            int v = val.get(s.charAt(i));
            if (i + 1 < s.length() && v < val.get(s.charAt(i + 1))) {
                sum -= v;
            } else {
                sum += v;
            }
        }
        return sum;
    }

    /* 9) Phone keypad letter combinations — digit->letters map */
    public static List<String> phoneCombinations(String digits) {
        if (digits.isEmpty()) return List.of();
        Map<Character, String> map = Map.of(
                '2', "abc", '3', "def", '4', "ghi", '5', "jkl", '6', "mno", '7', "pqrs", '8', "tuv", '9', "wxyz"
        );
        List<String> res = new ArrayList<>();
        res.add("");
        for (char d : digits.toCharArray()) {
            List<String> next = new ArrayList<>();
            for (String p : res) {
                for (char c : map.get(d).toCharArray()) {
                    next.add(p + c);
                }
            }
            res = next;
        }
        return res;
    }

    /* 10) LRU (map-backed) via LinkedHashMap accessOrder=true */
    public static final class LRUCache {
        private final int cap;
        private final LinkedHashMap<Integer, Integer> map =
                new LinkedHashMap<>(16, 0.75f, true);

        public LRUCache(int capacity) {
            this.cap = capacity;
        }

        public int get(int k) {
            return map.getOrDefault(k, -1);
        }

        public void put(int k, int v) {
            map.put(k, v);
            if (map.size() > cap) {
                map.remove(map.keySet().iterator().next());
            }
        }
    }

    /* 11) Memoized Fibonacci — cache with Map */
    public static int fib(int n) {
        Map<Integer, Integer> memo = new HashMap<>();
        memo.put(0, 0);
        memo.put(1, 1);
        return fibMemo(n, memo);
    }

    private static int fibMemo(int n, Map<Integer, Integer> m) {
        Integer hit = m.get(n);
        if (hit != null) {
            return hit;
        }
        int v = fibMemo(n - 1, m) + fibMemo(n - 2, m);
        m.put(n, v);
        return v;
    }

    /* 12) Build Map<K, List<V>> (multimap) via computeIfAbsent */
    public static <K, V> Map<K, List<V>> groupToList(List<V> values, Function<V, K> keyFn) {
        Map<K, List<V>> g = new HashMap<>();
        for (V v : values) {
            g.computeIfAbsent(
                    keyFn.apply(v),
                    k -> new ArrayList<>()).add(v);
        }
        return g;
    }

    /* 13) Count distinct in window k — sliding window with Map freq */
    public static List<Integer> countDistinctInWindows(int[] a, int k) {
        Map<Integer, Integer> f = new HashMap<>();
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < a.length; i++) {
            f.merge(a[i], 1, Integer::sum);
            if (i >= k) {
                f.compute(
                        a[i - k],
                        (key, val) -> (val == 1) ? null : val - 1);
            }
            if (i >= k - 1) {
                ans.add(f.size());
            }
        }
        return ans;
    }

    /* 14) Are two strings anagrams? — map compare (educational; array faster) */
    public static boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) return false;
        Map<Character, Integer> f = new HashMap<>();
        for (char c : s.toCharArray()) {
            f.merge(c, 1, Integer::sum);
        }
        for (char c : t.toCharArray()) {
            Integer cnt = f.get(c);
            if (cnt == null) {
                return false;
            }
            if (cnt == 1) {
                f.remove(c);
            }
            else {
                f.put(c, cnt - 1);
            }
        }
        return f.isEmpty();
    }

    /* 15) Sort by frequency descending then value ascending */
    public static List<Integer> sortByFreqThenValue(int[] nums) {
        Map<Integer, Integer> f = new HashMap<>();
        for (int x : nums) {
            f.merge(x, 1, Integer::sum);
        }
        return f.entrySet().stream()
                .sorted((e1, e2) -> {
                    int c = Integer.compare(e2.getValue(), e1.getValue());
                    return (c != 0) ? c : Integer.compare(e1.getKey(), e2.getKey());
                })
                .map(Map.Entry::getKey).toList();
    }

    // tiny demo
    public static void main(String[] args) {
        System.out.println(charFreq("banana"));                         // {b=1, a=3, n=2}
        System.out.println(wordFreq("To be, or not to be."));           // {to=2, be=2, or=1, not=1}
        System.out.println(Arrays.toString(twoSum(new int[]{2, 7, 11, 15}, 9))); // [0,1]
        System.out.println(groupAnagrams(new String[]{"eat", "tea", "tan", "ate", "nat", "bat"}));
        System.out.println(firstUniqChar("leetcode"));                   // 0
        System.out.println(topKFrequent(new int[]{1, 1, 1, 2, 2, 3}, 2));     // [1,2]
        System.out.println(isIsomorphic("egg", "add"));                   // true
        System.out.println(romanToInt("MCMXCIV"));                       // 1994
        System.out.println(phoneCombinations("23"));                     // [ad, ae, af, bd, ...]
        LRUCache lru = new LRUCache(2);
        lru.put(1, 1);
        lru.put(2, 2);
        lru.get(1);
        lru.put(3, 3);
        System.out.println(lru.get(2)); // -1
        System.out.println(fib(10));                                     // 55
        System.out.println(groupToList(List.of("apple", "apricot", "banana"), w -> w.charAt(0))); // {a=[apple, apricot], b=[banana]}
        System.out.println(countDistinctInWindows(new int[]{1, 2, 1, 3, 4, 2, 3}, 4)); // [3,4,4,3]
        System.out.println(isAnagram("anagram", "nagaram"));              // true
        System.out.println(sortByFreqThenValue(new int[]{2, 3, 1, 3, 2, 2, 4})); // [2,3,1,4]
    }
}
