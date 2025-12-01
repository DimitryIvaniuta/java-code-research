package com.code.research.interfc;

class Circle extends Shape implements Drawable {

    private final double r;

    public Circle(String color, double r) {
        super(color);
        this.r = r;
    }

    @Override
    public double area() {
        return Math.PI * r * r;
    }

    @Override
    public void draw() {
        System.out.println("Drawing circle " + color);
    }
}