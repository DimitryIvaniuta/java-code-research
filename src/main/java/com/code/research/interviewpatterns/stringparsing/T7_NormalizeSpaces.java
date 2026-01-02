package com.code.research.interviewpatterns.stringparsing;

public final class T7_NormalizeSpaces {
    public static String normalize(String s) {
        if (s == null) return "";
        s = s.trim();
        if (s.isEmpty()) return "";
        StringBuilder out = new StringBuilder();
        boolean prevSpace = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ') {
                if (!prevSpace) out.append(' ');
                prevSpace = true;
            } else {
                out.append(c);
                prevSpace = false;
            }
        }
        return out.toString();
    }
}
