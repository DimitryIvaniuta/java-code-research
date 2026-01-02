package com.code.research.interviewpatterns.stringparsing;

import java.util.LinkedHashMap;
import java.util.Map;

public final class T5_ParseSemicolonPairs {
    public static Map<String, String> parse(String s) {
        Map<String, String> map = new LinkedHashMap<>();
        if (s == null) return map;
        s = s.trim();
        if (s.isEmpty()) return map;

        for (String part : s.split(";")) {
            part = part.trim();
            if (part.isEmpty()) continue;
            String[] kv = part.split("=", 2);
            String key = kv[0].trim();
            String val = kv.length == 2 ? kv[1].trim() : "";
            if (!key.isEmpty()) map.put(key, val);
        }
        return map;
    }
}
