package demos.creational;

import common.creational.prototype.*;

public class PrototypePattern {
    public static void run() {
        System.out.println("Running prototype pattern demo...");

        ShapeCache.loadCache();

        Shape clonedShape = ShapeCache.getShape("1");
        System.out.println("Shape : " + clonedShape.getType());

        Shape clonedShape2 = ShapeCache.getShape("2");
        System.out.println("Shape : " + clonedShape2.getType());

        Shape clonedShape3 = ShapeCache.getShape("3");
        System.out.println("Shape : " + clonedShape3.getType());

        System.out.println("");
    }
}
