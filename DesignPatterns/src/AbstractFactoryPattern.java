import common.factory.*;
import common.interfaces.*;

public class AbstractFactoryPattern {
    public static void run() {
        System.out.println("Running abstract factory pattern demo...");

        AbstractFactory shapeFactory = FactoryProducer.getFactory("SHAPE");

        Shape circle = shapeFactory.getShape("CIRCLE");
        circle.draw();

        Shape rectangle = shapeFactory.getShape("RECTANGLE");
        rectangle.draw();

        Shape square = shapeFactory.getShape("SQUARE");
        square.draw();

        AbstractFactory colorFactory = FactoryProducer.getFactory("COLOR");

        Color red = colorFactory.getColor("RED");
        red.fill();

        Color green = colorFactory.getColor("GREEN");
        green.fill();

        Color blue = colorFactory.getColor("BLUE");
        blue.fill();

        System.out.println("");
    }
}
