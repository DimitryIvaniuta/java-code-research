package com.code.research.enums;

public enum BasicOperation implements Operation {
    PLUS {
        @Override
        public double apply(double a, double b) {
            return a + b + calculateDefault(a, b);
        }
    },
    MINUS {
        @Override
        public double apply(double x, double y) {
            return x - y;
        }
    },
    TIMES {
        @Override
        public double apply(double x, double y) {
            return x * y;
        }
    },
    DIVIDE {
        @Override
        public double apply(double x, double y) {
            return x / y;
        }
    };
}
