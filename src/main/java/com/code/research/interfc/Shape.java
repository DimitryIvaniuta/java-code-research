package com.code.research.interfc;

abstract class Shape {
    protected String color;

    public Shape(String color) {      // constructor allowed
        this.color = color;
    }

    public abstract double area();    // abstract method
}
