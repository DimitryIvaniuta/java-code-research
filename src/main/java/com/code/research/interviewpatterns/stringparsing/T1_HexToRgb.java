package com.code.research.interviewpatterns.stringparsing;

public final class T1_HexToRgb {
    public static int[] hexToRgb(String hex) {
        if (hex == null) throw new IllegalArgumentException();
        hex = hex.trim();
        if (!hex.startsWith("#")) throw new IllegalArgumentException();
        String s = hex.substring(1);
        if (s.length() == 3) {
            s = "" + s.charAt(0) + s.charAt(0) + s.charAt(1) + s.charAt(1) + s.charAt(2) + s.charAt(2);
        }
        if (s.length() != 6) throw new IllegalArgumentException();
        int r = Integer.parseInt(s.substring(0, 2), 16);
        int g = Integer.parseInt(s.substring(2, 4), 16);
        int b = Integer.parseInt(s.substring(4, 6), 16);
        return new int[]{r, g, b};
    }
}
