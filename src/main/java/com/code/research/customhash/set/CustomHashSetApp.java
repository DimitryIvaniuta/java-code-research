package com.code.research.customhash.set;

public class CustomHashSetApp {
    public static void main(String[] args) {
        CustomHashSet<String> set = new CustomHashSet<>();
        set.add("A");
        set.add("A");   // ignored
        set.add(null);  // allowed
        System.out.println(set.contains("A")); // true
        set.remove(null);
        for (String s : set) System.out.println(s);
    }
}
