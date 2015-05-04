package demos.creational;

import common.creational.factory.*;
import common.interfaces.*;

public class FactoryPattern {
    public static void run() {
        System.out.println("Running factory pattern demo...");

        ShapeFactory factory = new ShapeFactory();

        Shape circle = factory.getShape("CIRCLE");
        circle.draw();

        Shape rectangle = factory.getShape("RECTANGLE");
        rectangle.draw();

        Shape square = factory.getShape("SQUARE");
        square.draw();

        System.out.println("");
    }
}
