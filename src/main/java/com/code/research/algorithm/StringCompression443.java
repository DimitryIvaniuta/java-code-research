package com.code.research.algorithm;

public final class StringCompression443 {
    // Compresses chars in-place and returns the new length
    public static int compress(char[] chars) {
        // total input length
        int n = chars.length;
        // write: next output slot, read: scan pointer
        int write = 0;
        int read = 0;

        while (read < n) {
            // current run character
            char ch = chars[read];
            // start index of this run
            int start = read;
            // advance to end of run
            while (read < n && chars[read] == ch) {
                read++;
            }
            // run length
            int count = read - start;
            // always write the character once
            chars[write++] = ch;
            // if run is longer than 1, write its length digits
            if (count > 1) {
                String s = Integer.toString(count);
                for (int i = 0; i < s.length(); i++) chars[write++] = s.charAt(i);
            }
        }
        // new logical length; contents beyond can be ignored
        return write;
    }

    // tiny demo
    public static void main(String[] args) {
        char[] a = {'a', 'a', 'b', 'b', 'c', 'c', 'c'};
        int len = compress(a);
        System.out.println(len + " -> " + new String(a, 0, len)); // 6 -> a2b2c3

        char[] b = {'a'};
        len = compress(b);
        System.out.println(len + " -> " + new String(b, 0, len)); // 1 -> a

        char[] c = {'a', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b'};
        len = compress(c);
        System.out.println(len + " -> " + new String(c, 0, len)); // 4 -> ab12
    }
}
