package com.code.research.interviewpatterns.stringparsing;

public final class T3_TimeToSeconds {
    public static int toSeconds(String s) {
        if (s == null) throw new IllegalArgumentException();
        s = s.trim();
        if (s.length() != 8 || s.charAt(2) != ':' || s.charAt(5) != ':') throw new IllegalArgumentException();
        int hh = Integer.parseInt(s.substring(0, 2));
        int mm = Integer.parseInt(s.substring(3, 5));
        int ss = Integer.parseInt(s.substring(6, 8));
        if (hh < 0 || hh > 23 || mm < 0 || mm > 59 || ss < 0 || ss > 59) throw new IllegalArgumentException();
        return hh * 3600 + mm * 60 + ss;
    }
}
