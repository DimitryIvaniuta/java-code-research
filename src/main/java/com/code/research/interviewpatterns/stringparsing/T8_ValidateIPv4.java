package com.code.research.interviewpatterns.stringparsing;

public final class T8_ValidateIPv4 {
    public static boolean isValid(String ip) {
        if (ip == null) return false;
        ip = ip.trim();
        String[] parts = ip.split("\\.", -1);
        if (parts.length != 4) return false;

        for (String p : parts) {
            if (p.isEmpty() || p.length() > 3) return false;
            // no leading +/-, only digits
            for (int i = 0; i < p.length(); i++) if (!Character.isDigit(p.charAt(i))) return false;
            // avoid "01" if you want strict: disallow leading zeros except "0"
            if (p.length() > 1 && p.charAt(0) == '0') return false;

            int v = Integer.parseInt(p);
            if (v < 0 || v > 255) return false;
        }
        return true;
    }
}
