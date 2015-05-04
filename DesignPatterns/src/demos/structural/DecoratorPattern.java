package demos.structural;

import common.interfaces.*;
import common.structural.decorator.*;

public class DecoratorPattern {
    public static void run() {
        System.out.println("Running factory pattern demo...");

        Shape circle = new Circle();

        Shape redCircle = new RedShapeDecorator(new Circle());

        Shape redRectangle = new RedShapeDecorator(new Rectangle());

        System.out.println("Circle with normal border...");
        circle.draw();

        System.out.println("\nCircle with red border...");
        redCircle.draw();

        System.out.println("\nRectangle with red border...");
        redRectangle.draw();

        System.out.println("");
    }
}
