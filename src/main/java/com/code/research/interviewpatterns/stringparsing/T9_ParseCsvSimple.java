package com.code.research.interviewpatterns.stringparsing;

import java.util.ArrayList;
import java.util.List;

public final class T9_ParseCsvSimple {
    public static List<String> parse(String line) {
        List<String> res = new ArrayList<>();
        if (line == null) return res;
        for (String part : line.split(",", -1)) {
            res.add(part.trim());
        }
        return res;
    }
}
