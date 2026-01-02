package com.code.research.interviewpatterns;

public final class ColorHex {

    public static final class RGB {
        public final int r;
        public final int g;
        public final int b;

        public RGB(int r, int g, int b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }

        @Override
        public String toString() {
            return "RGB(" + r + "," + g + "," + b + ")";
        }
    }

    // Supports "#RRGGBB" and "#RGB"
    public static RGB hexToRgb(String hex) {
        if (hex == null) throw new IllegalArgumentException("hex is null");
        hex = hex.trim();

        if (!hex.startsWith("#")) {
            throw new IllegalArgumentException("hex must start with #");
        }

        String s = hex.substring(1);

        // Expand short form "#RGB" -> "#RRGGBB"
        if (s.length() == 3) {
            s = "" + s.charAt(0) + s.charAt(0)
                    + s.charAt(1) + s.charAt(1)
                    + s.charAt(2) + s.charAt(2);
        }

        if (s.length() != 6) {
            throw new IllegalArgumentException("hex must be #RGB or #RRGGBB");
        }

        int r = Integer.parseInt(s.substring(0, 2), 16);
        int g = Integer.parseInt(s.substring(2, 4), 16);
        int b = Integer.parseInt(s.substring(4, 6), 16);

        return new RGB(r, g, b);
    }

    public static void main(String[] args) {
        System.out.println(hexToRgb("#ff00aa")); // RGB(255,0,170)
        System.out.println(hexToRgb("#0fa"));    // RGB(0,255,170)
    }
}
