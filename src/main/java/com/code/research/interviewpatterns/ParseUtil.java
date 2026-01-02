package com.code.research.interviewpatterns;

import java.util.Objects;

/**
 * Copy-paste template for interview parsing tasks.
 * Small helpers: sanitize, require, digit checks, safe parsing, bounds checks.
 */
public final class ParseUtil {

    private ParseUtil() {
    }

    /**
     * Trim string; optionally treat null as empty.
     */
    public static String sanitize(String s, boolean nullAsEmpty) {
        if (s == null) return nullAsEmpty ? "" : null;
        return s.trim();
    }

    /**
     * Require condition; throw IllegalArgumentException with message.
     */
    public static void require(boolean condition, String message) {
        if (!condition) throw new IllegalArgumentException(message);
    }

    /**
     * Require non-null; returns same reference for chaining.
     */
    public static <T> T requireNonNull(T value, String name) {
        return Objects.requireNonNull(value, name + " is null");
    }

    /**
     * True for '0'..'9'.
     */
    public static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    /**
     * True for hex digit: 0-9, a-f, A-F.
     */
    public static boolean isHexDigit(char c) {
        return (c >= '0' && c <= '9')
                || (c >= 'a' && c <= 'f')
                || (c >= 'A' && c <= 'F');
    }

    /**
     * Parses signed base-10 int from [start, end). Throws if invalid.
     */
    public static int parseIntDec(String s, int start, int end) {
        requireNonNull(s, "s");
        require(start >= 0 && end <= s.length() && start < end, "bad range");
        int i = start;

        boolean neg = false;
        char first = s.charAt(i);
        if (first == '+' || first == '-') {
            neg = (first == '-');
            i++;
            require(i < end, "sign without digits");
        }

        long val = 0;
        for (; i < end; i++) {
            char c = s.charAt(i);
            require(isDigit(c), "not a digit: " + c);
            val = val * 10 + (c - '0');
            require(val <= Integer.MAX_VALUE + (neg ? 1L : 0L), "int overflow");
        }
        int res = (int) val;
        return neg ? -res : res;
    }

    /**
     * Parses unsigned base-16 int from [start, end). Throws if invalid.
     */
    public static int parseIntHex(String s, int start, int end) {
        requireNonNull(s, "s");
        require(start >= 0 && end <= s.length() && start < end, "bad range");

        int val = 0;
        for (int i = start; i < end; i++) {
            char c = s.charAt(i);
            require(isHexDigit(c), "not a hex digit: " + c);
            val = (val << 4) + hexValue(c);
        }
        return val;
    }

    private static int hexValue(char c) {
        if (c >= '0' && c <= '9') return c - '0';
        if (c >= 'a' && c <= 'f') return 10 + (c - 'a');
        return 10 + (c - 'A'); // A-F
    }

    /**
     * Safe substring with bounds checks.
     */
    public static String slice(String s, int start, int end) {
        requireNonNull(s, "s");
        require(start >= 0 && end >= start && end <= s.length(), "bad slice range");
        return s.substring(start, end);
    }

    /**
     * Checks exact length.
     */
    public static void requireLength(String s, int expected, String name) {
        requireNonNull(s, name);
        require(s.length() == expected, name + " must have length " + expected);
    }

    /**
     * Checks a fixed char at position.
     */
    public static void requireCharAt(String s, int pos, char expected, String name) {
        requireNonNull(s, name);
        require(pos >= 0 && pos < s.length(), "pos out of range");
        require(s.charAt(pos) == expected, name + " must have '" + expected + "' at " + pos);
    }

    /**
     * Example: parse date "YYYY-MM-DD" into ints (basic format validation).
     */
    public static int[] parseIsoDate(String raw) {
        String s = sanitize(raw, false);
        requireNonNull(s, "date");
        requireLength(s, 10, "date");
        requireCharAt(s, 4, '-', "date");
        requireCharAt(s, 7, '-', "date");

        int year = parseIntDec(s, 0, 4);
        int month = parseIntDec(s, 5, 7);
        int day = parseIntDec(s, 8, 10);

        require(month >= 1 && month <= 12, "month out of range");
        require(day >= 1 && day <= 31, "day out of range");
        return new int[]{year, month, day};
    }

    // quick demo
    public static void main(String[] args) {
        System.out.println(parseIntHex("ff00aa", 0, 6)); // 16711850
        int[] d = parseIsoDate("2026-01-02");
        System.out.println(d[0] + "-" + d[1] + "-" + d[2]);
    }
}
