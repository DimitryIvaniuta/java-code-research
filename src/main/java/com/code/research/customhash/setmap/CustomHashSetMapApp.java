package com.code.research.customhash.setmap;

public class CustomHashSetMapApp {
    public static void main(String[] args) {
        CustomHashSet<String> s = new CustomHashSet<>();
        s.add("A");
        s.add("A");
        s.add(null);

        System.out.println(s.contains("A")); // true
        System.out.println(s);               // order not guaranteed
    }
}
