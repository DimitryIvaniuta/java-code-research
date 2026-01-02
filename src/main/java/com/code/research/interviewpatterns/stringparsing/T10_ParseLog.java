package com.code.research.interviewpatterns.stringparsing;

public final class T10_ParseLog {
    public static String[] parse(String s) {
        if (s == null) throw new IllegalArgumentException();
        s = s.trim();
        if (!s.startsWith("[")) throw new IllegalArgumentException();
        int close = s.indexOf(']');
        if (close < 0) throw new IllegalArgumentException();

        String level = s.substring(1, close).trim();

        // naive: message = text after date (if present)
        // We'll just take everything after first space following the bracket.
        int firstSpace = s.indexOf(' ', close + 1);
        if (firstSpace < 0) return new String[]{level, ""};

        String rest = s.substring(firstSpace + 1).trim();

        // If rest starts with date "YYYY-MM-DD", skip it
        if (rest.length() >= 10 && rest.charAt(4) == '-' && rest.charAt(7) == '-') {
            int nextSpace = rest.indexOf(' ', 10);
            if (nextSpace >= 0) rest = rest.substring(nextSpace + 1).trim();
            else rest = "";
        }

        return new String[]{level, rest};
    }
}
