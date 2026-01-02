package com.code.research.interviewpatterns.stringparsing;

public final class T6_ExtractFirstInt {
    public static int firstInt(String s) {
        if (s == null) throw new IllegalArgumentException();
        int n = s.length();
        int i = 0;
        while (i < n && !Character.isDigit(s.charAt(i))) i++;
        if (i == n) throw new IllegalArgumentException("no number");
        long val = 0;
        while (i < n && Character.isDigit(s.charAt(i))) {
            val = val * 10 + (s.charAt(i) - '0');
            if (val > Integer.MAX_VALUE) throw new IllegalArgumentException("overflow");
            i++;
        }
        return (int) val;
    }
}
