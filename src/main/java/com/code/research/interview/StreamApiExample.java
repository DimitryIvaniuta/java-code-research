package com.code.research.interview;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class StreamApiExample {

    public enum Status {
        ACTIVE, INACTIVE
    }

    public record Employee(int id, String name, double salary, Status status) {
    }

    public double totalEmployers(List<Employee> employees) {
        return employees.stream()
                .filter(e -> e.status() == Status.ACTIVE)
                .sorted(Comparator.comparing(Employee::salary))
                .mapToDouble(Employee::salary)
                .sum();

    }

    public int totalSum(List<Integer> numbers) {
        return numbers.stream().reduce(0, Integer::sum);
    }

    public int totalStrSum(List<String> numbersStr) {
        return numbersStr.stream().mapToInt(Integer::parseInt).sum();
    }


    public String totalStrJoin(List<String> numbersStr) {
        return numbersStr.stream().collect(
                Collectors.joining(", ", "{", "}")
        );
    }

    public Map<String, Integer> mergeMap(List<String> values) {
        return values.stream()
                .filter(s -> s.length() > 3)
                .collect(
                        Collectors.toMap(
                                s -> s.substring(0, 3),
                                String::length,
                                (v1, v2) -> v1
                        )
                );
    }

    public int flattenIntCollectionsSum(List<List<Integer>> coll) {
        return coll.stream()
                .flatMapToInt(list -> list.stream().mapToInt(i -> i))
                .sum();
    }


    public int flattenIntCollectionsSumNonNull(List<List<Integer>> coll) {
        if (coll == null) return 0;
        return coll.stream()
                .filter(Objects::nonNull)
                .flatMapToInt(
                        list -> list.stream()
                                .filter(Objects::nonNull)
                                .mapToInt(Integer::intValue)
                ).sum();

    }

    public int flattenIntCollectionsSumMap(List<List<Integer>> coll) {
        if (coll == null) return 0;
        return coll.stream()
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .sum();
    }

    public Map<Character, List<String>> groupCollection(List<String> coll) {
        return coll.stream()
                .filter(Objects::nonNull)
                .filter(StringUtils::isNotEmpty)
                .collect(
                        Collectors.groupingBy(
                                s -> s.charAt(0)
                        )
                );
    }

    public void processParallelStream(List<Integer> numbers) {
        numbers.parallelStream()
                .forEach(n -> System.out.println("Number: " + n + " Thread: " + Thread.currentThread().getName()));
    }

    public String customCollector(List<String> coll) {
        return coll.stream()
                .filter(Objects::nonNull)
                .collect(
                        Collector.of(
                                StringBuilder::new,
                                StringBuilder::append,
                                (sb1, sb2) -> {
                                    sb1.append(sb2);
                                    return sb1;
                                },
                                StringBuilder::toString
                        )
                );
    }

    public List<String> customCollectorEmployeeListName(List<Employee> coll) {
        return coll.stream()
                .filter(Objects::nonNull)
                .peek(e -> System.out.println("Employee: " + e))
                .collect(
                        Collector.of(
                                ArrayList::new,
                                (list, e) -> {
                                    if (e.name() != null) {
                                        list.add(e.name());
                                    }
                                },
                                (l1, l2) -> {
                                    l1.addAll(l2);
                                    return l1;
                                },
                                Collector.Characteristics.IDENTITY_FINISH
                        )
                );
    }

    private boolean isInt32(String s) {
        // optional sign + digits only
        if (!s.matches("[+-]?\\d+")) return false;

        // strip sign and leading zeros for clean length compare
        boolean neg = s.charAt(0) == '-';
        int start = (s.charAt(0) == '+' || s.charAt(0) == '-') ? 1 : 0;
        String digits = stripLeadingZeros(s.substring(start));

        if (digits.isEmpty()) return true; // was "0", "+0", "-0"
        if (digits.length() < 10) return true;
        if (digits.length() > 10) return false;

        // length == 10 → lexicographic compare to int bounds
        String limit = neg ? "2147483648" : "2147483647";
        return digits.compareTo(limit) <= 0;
    }

    private String stripLeadingZeros(String x) {
        int i = 0, n = x.length();
        while (i < n && x.charAt(i) == '0') i++;
        return (i == n) ? "" : x.substring(i);
    }


    public int[] listToIntArray(List<String> list) {
        return list.stream()
                .filter(s -> StringUtils.isNotEmpty(s) && isInt32(s))
                .distinct()
                .skip(2)
                .limit(4)
                .mapToInt(Integer::parseInt).toArray();
    }

    public boolean checkAllNumbersEven(List<Integer> ints){
        return ints.stream().allMatch(i-> i % 2 == 0 );
    }

    public Map<Boolean, List<Integer>> groupIntsFromStringL(List<String> coll) {
        return coll.stream()
                .filter(Objects::nonNull)
                .map(Integer::parseInt)
                .collect(
                        Collectors.partitioningBy(
                                n -> n % 2 == 0
                        )
                );
    }

    public IntSummaryStatistics summaryStatistics(List<Integer> ints){
        return ints.stream().mapToInt(Integer::intValue).summaryStatistics();
    }
}
