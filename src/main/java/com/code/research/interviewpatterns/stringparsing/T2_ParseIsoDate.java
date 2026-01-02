package com.code.research.interviewpatterns.stringparsing;

public final class T2_ParseIsoDate {
    public static int[] parse(String s) {
        if (s == null) throw new IllegalArgumentException();
        s = s.trim();
        if (s.length() != 10 || s.charAt(4) != '-' || s.charAt(7) != '-') throw new IllegalArgumentException();
        int y = Integer.parseInt(s.substring(0, 4));
        int m = Integer.parseInt(s.substring(5, 7));
        int d = Integer.parseInt(s.substring(8, 10));
        if (m < 1 || m > 12 || d < 1 || d > 31) throw new IllegalArgumentException();
        return new int[]{y, m, d};
    }
}
