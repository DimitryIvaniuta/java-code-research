package com.code.research.datastructures.hash;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
public class FrequentElements {

    public static void main(String[] args) {
        int frequentElementsCount = 3;
        Map<Integer, Integer> res = new HashMap<>();
        int[] arr = {4, 4, 1, 1, 1, 2, 2, 3, 5, 5, 5, 5, 5};
        for (int v : arr) {
            res.merge(v, 1, Integer::sum);
        }
        log.info("Map:{}", res);
        TreeMap<Integer, Integer> treeMap = new TreeMap<>(res);
        int[] resInt = treeMap.keySet().stream()
                .filter(k -> treeMap.getOrDefault(k, 0) >= frequentElementsCount)
                .mapToInt(Integer::intValue).toArray();
        log.info("Elements :{}", resInt);
    }

}
