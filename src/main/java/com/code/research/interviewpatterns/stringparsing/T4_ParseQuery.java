package com.code.research.interviewpatterns.stringparsing;

import java.util.LinkedHashMap;
import java.util.Map;

public final class T4_ParseQuery {
    public static Map<String, String> parse(String q) {
        Map<String, String> map = new LinkedHashMap<>();
        if (q == null) return map;
        q = q.trim();
        if (q.isEmpty()) return map;

        String[] pairs = q.split("&");
        for (String p : pairs) {
            if (p.isEmpty()) continue;
            String[] kv = p.split("=", 2);
            String k = kv[0];
            String v = kv.length == 2 ? kv[1] : "";
            map.put(k, v.replace('+', ' '));
        }
        return map;
    }
}
