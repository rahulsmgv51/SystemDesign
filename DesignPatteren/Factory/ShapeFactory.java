package DesignPatteren.Factory;

import java.awt.*;

public class ShapeFactory {
    private ShapeFactory() {
        //Prevent instantiation
    }
    public static Shape getFactory(String shapeType) {
        if (shapeType == null || shapeType.isBlank()) {
            throw new IllegalArgumentException("Shape type cannot be null or empty");
        }

        switch (shapeType.toUpperCase()) {
            case "CIRCLE":
                return new Circle();

            case "SQUARE":
                return new Square();

            case "TRIANGLE":
                return new Triangle();

            default:
                throw new IllegalArgumentException("Unsupported Shape Type: " + shapeType);
        }
    }
}
