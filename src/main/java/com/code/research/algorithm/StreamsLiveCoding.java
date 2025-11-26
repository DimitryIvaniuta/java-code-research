package com.code.research.algorithm;

import java.util.*;
import java.util.function.Function;
import java.util.stream.*;

public final class StreamsLiveCoding {
    /* 1) Sum of squares of even numbers */
    public static int sumEvenSquares(int[] a) {
        return Arrays.stream(a)                   // IntStream
                .filter(x -> x % 2 == 0)    // keep evens
                .map(x -> x * x)            // square
                .sum();                     // sum
    }

    /* 2) Word frequency (lowercased, split by non-letters) */
    public static Map<String, Long> wordFreq(String text) {
        return Arrays.stream(
                        text.toLowerCase()
                                .split("[^a-z0-9]+")) // words
                .filter(s -> !s.isEmpty())
                .collect(
                        Collectors.groupingBy(
                                Function.identity(),
                                Collectors.counting())
                );
    }

    /* 3) Top-K frequent integers (value -> count), ties arbitrary */
    public static List<Integer> topKFrequent(int[] nums, int k) {
        Map<Integer, Long> freq = Arrays
                .stream(nums)
                .boxed()
                .collect(
                        Collectors.groupingBy(
                                Function.identity(), Collectors.counting())
                );
        return freq.entrySet()
                .stream()
                .sorted(
                        Map.Entry.<Integer, Long>comparingByValue()
                                .reversed()
                )
                .limit(k)
                .map(Map.Entry::getKey)
                .toList();
    }

    /* 4) Group Anagrams -> map sorted-key to list */
    public static List<List<String>> groupAnagrams(String[] strs) {
        return new ArrayList<>(
                Arrays.stream(strs)
                        .collect(
                                Collectors.groupingBy(
                                        s -> s.chars()
                                                .sorted()
                                                .collect(
                                                        StringBuilder::new,
                                                        (sb, c) -> sb.append((char) c),
                                                        StringBuilder::append)
                                                .toString()
                                ))
                        .values()
        );
    }

    /* 5) First non-repeating character in a string (preserve order) */
    public static Optional<Character> firstNonRepeating(String s) {
        Map<Character, Long> freq =
                s.chars().mapToObj(c -> (char) c)
                        .collect(
                                Collectors.groupingBy(
                                        Function.identity(),
                                        LinkedHashMap::new,
                                        Collectors.counting()));
        return freq.entrySet()
                .stream()
                .filter(e -> e.getValue() == 1)
                .map(Map.Entry::getKey)
                .findFirst(); // empty if none
    }

    /* 6) Palindrome-permutation check (letters+digits only, case-insensitive) */
    public static boolean canPermutePalindrome(String s) {
        long odd = s.chars()
                .filter(Character::isLetterOrDigit)
                .map(Character::toLowerCase)
                .boxed()
                .collect(
                        Collectors.groupingBy(
                                Function.identity(),
                                Collectors.counting()))
                .values()
                .stream()
                .filter(c -> (c & 1L) == 1L)
                .count();
        return odd <= 1;
    }

    /* 7) Merge two int arrays, dedupe, sort ascending */
    public static int[] mergeDistinctSorted(int[] a, int[] b) {
        return IntStream.concat(
                        Arrays.stream(a),
                        Arrays.stream(b)
                )
                .distinct()
                .sorted()
                .toArray();
    }

    /* 8) K-th largest (1-based). Returns OptionalInt.empty() if k out of range */
    public static OptionalInt kthLargest(int[] nums, int k) {
        if (k <= 0 || k > nums.length) return OptionalInt.empty();
        return Arrays.stream(nums)
                .boxed()
                .sorted(
                        Comparator.reverseOrder()
                )
                .skip(k - 1L)
                .mapToInt(Integer::intValue)
                .findFirst();
    }

    /* 9) Longest string (by length); tie -> first occurrence */
    public static Optional<String> longestString(List<String> list) {
        return list.stream()
                .max(
                        Comparator.comparingInt(String::length)
                );
    }

    /* 10) Partition integers to even/odd lists */
    public static Map<Boolean, List<Integer>> partitionEvenOdd(int[] a) {
        return Arrays.stream(a).boxed()
                .collect(
                        Collectors.partitioningBy(x -> x % 2 == 0)
                );
    }

    /* 11) Average word length by first letter (lowercased) */
    public static Map<Character, Double> averageByFirstLetter(List<String> words) {
        return words.stream()
                .filter(w -> !w.isEmpty())
                .collect(Collectors.groupingBy(w -> Character.toLowerCase(w.charAt(0)),
                        Collectors.averagingInt(String::length)));
    }

    /* 12) Stable sort strings by length then lexicographically */
    public static List<String> sortByLenThenLex(List<String> words) {
        return words.stream()
                .sorted(
                        Comparator.comparingInt(String::length)
                                .thenComparing(Comparator.naturalOrder()))
                .toList();
    }

    /* 13) Join CSV after trimming/ignoring empties */
    public static String joinCsv(List<String> items) {
        return items.stream()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(
                        Collectors.joining(","));
    }

    /* 14) Flatten List<List<Integer>> and sum (pure streams) */
    public static int flattenSum(List<List<Integer>> lists) {
        return lists.stream()
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .sum();
    }

    /* 15) Build Map<K,V> with resolve function for duplicate keys */
    public static Map<String, Integer> toMapSumValues(List<Map.Entry<String, Integer>> entries) {
        return entries.stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::sum));
    }

    // tiny demo
    public static void main(String[] args) {
        System.out.println(sumEvenSquares(new int[]{1, 2, 3, 4}));                      // 20
        System.out.println(wordFreq("To be, or not to be: that is the question."));  // {to=2, be=2, ...}
        System.out.println(topKFrequent(new int[]{1, 1, 2, 3, 3, 3, 2, 2, 4}, 2));           // [2,3] (order may vary)
        System.out.println(groupAnagrams(new String[]{"eat", "tea", "tan", "ate", "nat", "bat"}));
        System.out.println(firstNonRepeating("aabbcddee"));                           // Optional[c]
        System.out.println(canPermutePalindrome("Tact Coa"));                         // true ("taco cat")
        System.out.println(Arrays.toString(mergeDistinctSorted(new int[]{1, 3, 3}, new int[]{2, 3, 4}))); // [1,2,3,4]
        System.out.println(kthLargest(new int[]{3, 2, 1, 5, 6, 4}, 2));                    // OptionalInt[5]
        System.out.println(longestString(List.of("a", "abcd", "abc")));                 // Optional[abcd]
        System.out.println(partitionEvenOdd(new int[]{1, 2, 3, 4}));                     // {false=[1,3], true=[2,4]}
        System.out.println(averageByFirstLetter(List.of("alpha", "array", "beta", "bit"))); // {a=..., b=...}
        System.out.println(sortByLenThenLex(List.of("bb", "a", "ab", "aa", "b")));        // [a, b, aa, ab, bb]
        System.out.println(joinCsv(List.of("  a ", "", "b", " c ")));                   // "a,b,c"
        System.out.println(flattenSum(List.of(List.of(1, 2), List.of(3), List.of()))); // 6
        System.out.println(toMapSumValues(List.of(Map.entry("x", 1), Map.entry("x", 2), Map.entry("y", 5)))); // {x=3,y=5}
    }
}
