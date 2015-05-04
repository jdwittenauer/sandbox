package demos;

import common.bridge.*;

public class BridgePattern {
    public static void run() {
        System.out.println("Running bridge pattern demo...");

        Shape redCircle = new Circle(10, 100, 100, new RedCircle());
        Shape greenCircle = new Circle(10, 100, 100, new GreenCircle());

        redCircle.draw();
        greenCircle.draw();

        System.out.println("");
    }
}
