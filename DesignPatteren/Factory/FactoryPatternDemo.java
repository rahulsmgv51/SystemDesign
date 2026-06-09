package DesignPatteren.Factory;

import java.awt.*;

public class FactoryPatternDemo {
    public static void main(String[] args) {
        System.out.println("FactoryPatternDemo");
        Shape circle = ShapeFactory.getFactory("Circle");
        Shape triangle = ShapeFactory.getFactory("Triangle");
        Shape square = ShapeFactory.getFactory("Square");

        circle.draw();
        triangle.draw();
        square.draw();
    }
}
