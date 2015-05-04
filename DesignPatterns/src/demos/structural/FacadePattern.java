package demos.structural;

import common.structural.facade.*;

public class FacadePattern {
    public static void run() {
        System.out.println("Running facade pattern demo...");

        ShapeMaker shapeMaker = new ShapeMaker();

        shapeMaker.drawCircle();
        shapeMaker.drawRectangle();
        shapeMaker.drawSquare();

        System.out.println("");
    }
}
