package demos;

import common.flyweight.*;

public class FlyweightPattern {
    private static final String colors[] = { "Red", "Green", "Blue", "White", "Black" };

    public static void run() {
        System.out.println("Running flyweight pattern demo...");

        for(int i = 0; i < 20; ++i) {
            Circle circle = (Circle)ShapeFactory.getCircle(getRandomColor());
            circle.setX(getRandomX());
            circle.setY(getRandomY());
            circle.setRadius(100);
            circle.draw();
        }

        System.out.println("");
    }

    private static String getRandomColor() {
        return colors[(int)(Math.random() * colors.length)];
    }
    private static int getRandomX() {
        return (int)(Math.random() * 100);
    }
    private static int getRandomY() {
        return (int)(Math.random() * 100);
    }
}
